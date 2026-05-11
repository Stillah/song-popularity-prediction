"""
Stage 3 – Spark ML Modeling
Predicts streams_in_the_first_month (regression task).

Two models:
  model1 – Linear Regression  (with StandardScaler + ElasticNet tuning)
  model2 – Gradient Boosted Tree Regressor (non-linear, handles skewed signals)

Design choices driven by EDA:
  * release_month, release_day_of_week, release_week_of_year are
    cyclically encoded via a reusable CyclicalEncoder
  * Target  is log1p-transformed (range 6.9 – 20.2) to tame extreme skew.
  * artist_avg_past_streams feature is also log1p-transformed (5M+ tier
    dominates: 19 M avg streams vs 1.9 M for debuts).
  * 80/20 stratified-style split with fixed seed for reproducibility.
  * GBT has no need for scaling; LR uses StandardScaler inside its Pipeline.
  * artist_avg_past_streams is log-transformed via a LogTransformer
"""

import os
import math

import pyspark.sql.functions as F
from pyspark.sql import SparkSession
from pyspark.sql.window import Window
from pyspark.sql.functions import row_number, monotonically_increasing_id
from pyspark.ml import Pipeline
from pyspark.ml.feature import VectorAssembler, StandardScaler
from pyspark.ml.regression import LinearRegression, GBTRegressor
from pyspark.ml.evaluation import RegressionEvaluator
from pyspark.ml.tuning import ParamGridBuilder, CrossValidator
from pyspark.ml.util import DefaultParamsReadable, DefaultParamsWritable
from pyspark.ml import Transformer
from pyspark.ml.param.shared import HasInputCol, HasOutputCol, Param, Params
from pyspark import keyword_only


# ===========================================================================
# Custom Transformers
# ===========================================================================


# pylint: disable=too-many-ancestors
class CyclicalEncoder(
    Transformer,
    HasInputCol,
    HasOutputCol,
    DefaultParamsReadable,
    DefaultParamsWritable,
):
    """
    Encodes a single integer column as two cyclical features.
    """
    # pylint: disable=protected-access
    period = Param(
        Params._dummy(),
        "period",
        "The cycle length (e.g. 12 for months, 7 for weekdays, 53 for weeks).",
    )
    # pylint: enable=protected-access

    @keyword_only
    def __init__(self, inputCol=None, outputCol=None, period=12):  # pylint: disable=unused-argument
        super().__init__()
        self._setDefault(period=12)
        # pylint: disable=no-member
        kwargs = self._input_kwargs
        # pylint: enable=no-member
        self._set(**kwargs)

    def get_period(self):
        """Get encoding period."""
        return self.getOrDefault(self.period)

    def _transform(self, dataset):
        """Apply cyclical encoding transformation to the dataset."""
        col_in = self.getInputCol()
        prefix = self.getOutputCol()
        period_val = self.get_period()   # renamed from 'p'
        angle = 2.0 * math.pi * F.col(col_in) / period_val
        dataset = dataset.withColumn(f"{prefix}_sin", F.sin(angle))
        dataset = dataset.withColumn(f"{prefix}_cos", F.cos(angle))
        return dataset


class LogTransformer(
    Transformer,
    HasInputCol,
    HasOutputCol,
    DefaultParamsReadable,
    DefaultParamsWritable,
):
    """
    Applies log1p to a numeric column and writes the result to outputCol.
    """

    @keyword_only
    def __init__(self, inputCol=None, outputCol=None):  # pylint: disable=unused-argument
        super().__init__()
        # pylint: disable=no-member
        kwargs = self._input_kwargs
        # pylint: enable=no-member
        self._set(**kwargs)

    def _transform(self, dataset):
        """Apply log1p transformation to the dataset."""
        return dataset.withColumn(
            self.getOutputCol(),
            F.log1p(F.col(self.getInputCol())),
        )
# pylint: enable=too-many-ancestors


# ===========================================================================
# Spark session
# ===========================================================================

TEAM = "team23"
WAREHOUSE = "project/hive/warehouse"

spark = (
    SparkSession.builder
    .appName(f"{TEAM} - spark ML")
    .master("yarn")
    .config("hive.metastore.uris", "thrift://hadoop-02.uni.innopolis.ru:9883")
    .config("spark.sql.warehouse.dir", WAREHOUSE)
    .config("spark.sql.avro.compression.codec", "snappy")
    .config("spark.sql.hive.convertMetastoreOrc", "false")
    .config("spark.sql.hive.convertMetastoreParquet", "false")
    .enableHiveSupport()
    .getOrCreate()
)

spark.sparkContext.setLogLevel("WARN")
print("Spark session started.")


def run(command: str) -> str:
    """Run command in the terminal."""
    return os.popen(command).read()


def hdfs_get(hdfs_path: str, local_path: str) -> None:
    """Get file content in hdfs."""
    run(f"hdfs dfs -cat {hdfs_path}/*.csv > {local_path}")


# ===========================================================================
# 1. Read Hive table
# ===========================================================================

spark.sql("USE team23_projectdb")
spark.sql("SHOW DATABASES").show()
spark.sql("SHOW TABLES").show()

raw_df = spark.table("team23_projectdb.songs_part_bucket")
print(f"Total rows: {raw_df.count()}")
raw_df.printSchema()


# We apply log1p to the target here, before the split, because Spark ML
# Pipelines operate only on feature columns during cross-validation.
df = raw_df.withColumn(
    "label",
    F.log1p(F.col("streams_in_the_first_month"))
)
df = df.dropna()
print(f"Rows after dropna: {df.count()}")

enc_month = CyclicalEncoder(inputCol="release_month", outputCol="release_month", period=12)
enc_weekday = CyclicalEncoder(inputCol="release_day_of_week",
                              outputCol="release_day_of_week", period=7)
enc_week = CyclicalEncoder(inputCol="release_week_of_year",
                           outputCol="release_week_of_year", period=53)

log_artist = LogTransformer(
    inputCol="artist_avg_past_streams",
    outputCol="log_artist_avg_past_streams",
)

FEATURE_COLS = [
    # audio features (13) – key already cyclically encoded in Stage 1
    "af_danceability", "af_energy",
    "key_sin", "key_cos",
    "af_loudness", "af_mode",
    "af_speechiness", "af_acousticness", "af_instrumentalness",
    "af_liveness", "af_valence", "af_tempo", "af_time_signature",
    # track metadata (2)
    "duration_ms", "explicit",
    # seasonal (6) – cyclically encoded pairs
    "release_month_sin",        "release_month_cos",
    "release_day_of_week_sin",  "release_day_of_week_cos",
    "release_week_of_year_sin", "release_week_of_year_cos",
    # market availability (9)
    "num_markets",
    "is_market_us", "is_market_gb", "is_market_de", "is_market_jp",
    "is_market_br", "is_market_fr", "is_market_ca", "is_market_au",
    # artist history (2)
    "artist_prior_tracks_count",
    "log_artist_avg_past_streams",   # derived via LogTransformer
]
# Total: 32 features (29 original, seasonal integers replaced by 6 sin/cos
# pairs which adds 3 extra columns)

# Add a column that preserves the current order (assumes data already sorted)
df = df.withColumn("row_id", monotonically_increasing_id())

# Assign a guaranteed sequential row number based on that identifier
window = Window.orderBy("row_id")
df = df.withColumn("row_num", row_number().over(window))

assembler = VectorAssembler(inputCols=FEATURE_COLS, outputCol="features")

prep_pipeline = Pipeline(stages=[
    enc_month,
    enc_weekday,
    enc_week,
    log_artist,
    assembler,
])

# Fit the pipeline (extra columns will be ignored by VectorAssembler)
prep_model = prep_pipeline.fit(df)
data_vec = prep_model.transform(df).select("features", "label", "row_num")

# ===========================================================================
# Time-based split: first 80% → train, last 20% → test
# ===========================================================================

total_rows = df.count()
split_at = int(total_rows * 0.8)

print(f"Total rows: {total_rows}, split at: {split_at}")

train_data = data_vec.filter(F.col("row_num") <= split_at).drop("row_num")
test_data = data_vec.filter(F.col("row_num") >  split_at).drop("row_num")

train_data.cache()
test_data.cache()

print(f"Train rows: {train_data.count()} | Test rows: {test_data.count()}")

train_data.coalesce(1).write.mode("overwrite").format("json").save("project/data/train")
run("hdfs dfs -cat project/data/train/*.json > data/train.json")

test_data.coalesce(1).write.mode("overwrite").format("json").save("project/data/test")
run("hdfs dfs -cat project/data/test/*.json > data/test.json")

print("Train / test JSON saved.")


# ===========================================================================
# Shared evaluators
# ===========================================================================

evaluator_rmse = RegressionEvaluator(
    labelCol="label", predictionCol="prediction", metricName="rmse"
)
evaluator_r2 = RegressionEvaluator(
    labelCol="label", predictionCol="prediction", metricName="r2"
)
evaluator_mae = RegressionEvaluator(
    labelCol="label", predictionCol="prediction", metricName="mae"
)


# ===========================================================================
# 5. Model 1 – Linear Regression
#    Pipeline: StandardScaler → LinearRegression
#    Tuned HPs: regParam (regularisation strength)
#               elasticNetParam (L1/L2 mix: 0=Ridge, 1=Lasso)
# ===========================================================================
print("\n=== Model 1: Linear Regression ===")

scaler = StandardScaler(
    inputCol="features", outputCol="scaled_features",
    withStd=True, withMean=True,
)
lr = LinearRegression(
    featuresCol="scaled_features",
    labelCol="label",
    maxIter=200,
    solver="auto",
)

pipeline_lr = Pipeline(stages=[scaler, lr])

grid_lr = (
    ParamGridBuilder()
    .addGrid(lr.regParam,        [0.001, 0.01, 0.1])
    .addGrid(lr.elasticNetParam, [0.0,   0.5,  1.0])
    .build()
)

cv_lr = CrossValidator(
    estimator=pipeline_lr,
    estimatorParamMaps=grid_lr,
    evaluator=evaluator_rmse,
    numFolds=3,
    parallelism=5,
    seed=42,
)

cv_model_lr = cv_lr.fit(train_data)
model1 = cv_model_lr.bestModel

lr_best = model1.stages[-1]
print(f"Best regParam:        {lr_best.getRegParam()}")
print(f"Best elasticNetParam: {lr_best.getElasticNetParam()}")

model1.write().overwrite().save("project/models/model1")
run("hdfs dfs -get project/models/model1 models/model1")
print("model1 saved.")

pred_test_lr = model1.transform(test_data)
rmse1 = evaluator_rmse.evaluate(pred_test_lr)
r2_1 = evaluator_r2.evaluate(pred_test_lr)
mae1 = evaluator_mae.evaluate(pred_test_lr)

print(f"LR  RMSE (log scale): {rmse1:.4f}")
print(f"LR  R²   (log scale): {r2_1:.4f}")
print(f"LR  MAE  (log scale): {mae1:.4f}")

pred_test_lr.select("label", "prediction") \
    .coalesce(1).write.mode("overwrite").format("csv") \
    .option("sep", ",").option("header", "true") \
    .save("project/output/model1_predictions")
hdfs_get("project/output/model1_predictions", "output/model1_predictions.csv")
print("model1 predictions saved.")


# ===========================================================================
# 6. Model 2 – Gradient Boosted Tree Regressor
#    No scaling needed (tree-based, scale-invariant).
#    Tuned HPs: maxDepth (tree complexity / bias-variance tradeoff)
#               stepSize (learning / shrinkage rate)
# ===========================================================================
print("\n=== Model 2: GBT Regressor ===")

gbt = GBTRegressor(
    featuresCol="features",
    labelCol="label",
    maxIter=20,
    subsamplingRate=0.8,
    featureSubsetStrategy="auto",
    seed=42,
)

grid_gbt = (
    ParamGridBuilder()
    .addGrid(gbt.maxDepth, [3, 5, 7])
    .addGrid(gbt.stepSize, [0.05, 0.1])
    .build()
)

cv_gbt = CrossValidator(
    estimator=gbt,
    estimatorParamMaps=grid_gbt,
    evaluator=evaluator_rmse,
    numFolds=3,
    parallelism=5,
    seed=42,
)

cv_model_gbt = cv_gbt.fit(train_data)
model2 = cv_model_gbt.bestModel

print(f"Best maxDepth: {model2.getMaxDepth()}")
print(f"Best stepSize: {model2.getStepSize()}")

importances = sorted(
    zip(FEATURE_COLS, model2.featureImportances.toArray()),
    key=lambda x: -x[1],
)
print("\nTop-10 feature importances (GBT):")
for feat, imp in importances[:10]:
    print(f"  {feat:<40} {imp:.4f}")

model2.write().overwrite().save("project/models/model2")
run("hdfs dfs -get project/models/model2 models/model2")
print("model2 saved.")

pred_test_gbt = model2.transform(test_data)
rmse2 = evaluator_rmse.evaluate(pred_test_gbt)
r2_2 = evaluator_r2.evaluate(pred_test_gbt)
mae2 = evaluator_mae.evaluate(pred_test_gbt)

print(f"GBT RMSE (log scale): {rmse2:.4f}")
print(f"GBT R²   (log scale): {r2_2:.4f}")
print(f"GBT MAE  (log scale): {mae2:.4f}")

pred_test_gbt.select("label", "prediction") \
    .coalesce(1).write.mode("overwrite").format("csv") \
    .option("sep", ",").option("header", "true") \
    .save("project/output/model2_predictions")
hdfs_get("project/output/model2_predictions", "output/model2_predictions.csv")
print("model2 predictions saved.")


# ===========================================================================
# 7. Compare models
# ===========================================================================
print("\n=== Model Comparison ===")

lr_uid = lr_best.uid
gbt_uid = model2.uid
N_FEATURES = len(FEATURE_COLS)
gbt_num_trees = len(model2.trees)

comparison = spark.createDataFrame(
    [
        (
            f"LinearRegressionModel: uid={lr_uid}, numFeatures={N_FEATURES}",
            round(rmse1, 4),
            round(r2_1, 4),
            round(mae1, 4),
        ),
        (
            f"GBTRegressionModel: uid={gbt_uid}, numTrees={gbt_num_trees}, \
            numFeatures={N_FEATURES}",
            round(rmse2, 4),
            round(r2_2, 4),
            round(mae2, 4),
        ),
    ],
    ["model", "RMSE", "R2", "MAE"],
)
comparison.show(truncate=False)

comparison.coalesce(1) \
    .write.mode("overwrite").format("csv") \
    .option("sep", ",").option("header", "true") \
    .save("project/output/evaluation")
run("hdfs dfs -cat project/output/evaluation/*.csv > output/evaluation.csv")
print("evaluation.csv saved.")

# ===========================================================================
# 8. Sanity check
# ===========================================================================
print("\n=== Sanity check (original-scale factor) ===")
print(f"LR  exp(RMSE) ≈ {math.exp(rmse1):.2f}×")
print(f"GBT exp(RMSE) ≈ {math.exp(rmse2):.2f}×")

spark.stop()
print("Done.")
