# Stage 3 – Machine Learning Modeling
 
## Overview
 
In this stage we built and evaluated two regression models to predict
`streams_in_the_first_month` — the total number of streams a song accumulates
in its first 30 days after release. The models are trained on the Hive table
`songs_part_bucket` created in Stage 2, using 32 features derived from the 29
engineered columns produced in Stage 1.
 
The full pipeline is automated through `scripts/stage3.sh`, which runs the
Spark ML job, validates outputs, and checks code quality.
 
---
 
## Files Added in Stage 3
 
### `scripts/model.py`
The main Spark ML script submitted via `spark-submit`. It connects to Hive,
reads the partitioned and bucketed table, applies feature engineering through
a preprocessing pipeline of custom transformers, builds and tunes two models
using cross-validated grid search, saves both models and their predictions to
HDFS, then exports everything locally.
 
### `scripts/model.ipynb`
An interactive Jupyter notebook containing the same logic as `model.py`. Used
during development to inspect intermediate DataFrames, verify schema, and
examine metric outputs before automating with `spark-submit`.
 
### `scripts/stage3.sh`
The main entry point for Stage 3. Cleans stale local model folders, runs
`model.py` via `spark-submit`, then calls the validation script and pylint
check. Produces a full run log in `output/model_output.txt`.
 
### `scripts/validate_stage3.py`
Checks that all expected output files and directories exist and are non-empty.
Writes a pass/fail report to `output/validate_stage3.txt`.
 
### `scripts/pylint_stage3.sh`
Runs `pylint` on `model.py` and `validate_stage3.py` and saves results to
`output/pylint_stage3.txt`.
 
---
 
## Output Files
 
| File | Description |
|------|-------------|
| `data/train.json` | Training split (80%) saved as JSON |
| `data/test.json` | Test split (20%) |
| `output/model1_predictions.csv` | `label` and `prediction` from the best Linear Regression model |
| `output/model2_predictions.csv` | `label` and `prediction` from the best GBT model |
| `output/evaluation.csv` | Side-by-side model comparison: model name, RMSE, R² |
| `output/model_output.txt` | Full stdout log from `spark-submit` |
| `output/validate_stage3.txt` | Validation pass/fail report |
| `output/pylint_stage3.txt` | Pylint output for both scripts |
| `models/model1/` | Saved PipelineModel (StandardScaler + LR) in Spark native format |
| `models/model2/` | Saved GBTRegressionModel in Spark native format |
 
---
 
## Data Source
 
We read directly from `team23_projectdb.songs_part_bucket` — the table
partitioned by `release_month` and bucketed by `num_markets` that was built in
Stage 2. Using this optimised table rather than the raw external table is
consistent with the Stage 2 design and satisfies the project checklist
requirement to run ML on the partitioned and bucketed Hive table.
 
---
 
## Feature Engineering Pipeline
 
Before splitting the data or training any model, we run a preprocessing
pipeline with four custom transformer stages followed by a vector assembler.
 
### Custom transformers
 
**`CyclicalEncoder`** encodes a single integer column as a sin/cos pair,
capturing its cyclical nature. Three instances are used:
 
| Instance | Input column | Period | Output columns |
|----------|-------------|--------|----------------|
| `enc_month` | `release_month` | 12 | `release_month_sin`, `release_month_cos` |
| `enc_weekday` | `release_day_of_week` | 7 | `release_day_of_week_sin`, `release_day_of_week_cos` |
| `enc_week` | `release_week_of_year` | 53 | `release_week_of_year_sin`, `release_week_of_year_cos` |
 
Without cyclical encoding, a linear model would treat December (month 12) and
January (month 1) as eleven steps apart when they are actually adjacent. The
sin/cos encoding places them correctly close together in feature space.
 
`key_sin` and `key_cos` were already produced in Stage 1 and arrive in the
Hive table pre-encoded; no further transformation is needed for those.
 
**`LogTransformer`** applies `log1p` to a single column and writes the result
to a new column, leaving the original intact. One instance is used:
 
| Input column | Output column |
|-------------|---------------|
| `artist_avg_past_streams` | `log_artist_avg_past_streams` |
 
The raw feature spans several orders of magnitude (the Stage 2 EDA momentum
table shows a 7× jump between the 1M–5M and 5M+ tiers). Log-transforming it
compresses this range and makes the relationship closer to linear. The original
column is kept in the DataFrame; only the log version enters the assembler.
 
### Final feature vector (32 columns)
 
| Group | Columns | Count |
|-------|---------|-------|
| Audio features | `af_danceability`, `af_energy`, `key_sin`, `key_cos`, `af_loudness`, `af_mode`, `af_speechiness`, `af_acousticness`, `af_instrumentalness`, `af_liveness`, `af_valence`, `af_tempo`, `af_time_signature` | 13 |
| Track metadata | `duration_ms`, `explicit` | 2 |
| Release timing | `release_month_sin`, `release_month_cos`, `release_day_of_week_sin`, `release_day_of_week_cos`, `release_week_of_year_sin`, `release_week_of_year_cos` | 6 |
| Market availability | `num_markets`, `is_market_us`, `is_market_gb`, `is_market_de`, `is_market_jp`, `is_market_br`, `is_market_fr`, `is_market_ca`, `is_market_au` | 9 |
| Artist history | `artist_prior_tracks_count`, `log_artist_avg_past_streams` | 2 |
 
The count increases from 29 (Stage 1 columns) to 32 because each of the three
raw seasonal integer columns is replaced by two sin/cos columns.
 
---
 
## Key Decisions and Justification
 
### 1. Log-transforming the target variable
 
The raw target `streams_in_the_first_month` is severely right-skewed: minimum
around 1 000 streams, maximum over 600 million, mean only 2.8 million. We
apply `log1p` before splitting or training:
 
```
label = log1p(streams_in_the_first_month)
```
 
This maps the target to the range 6.9–20.2, making the distribution
approximately symmetric. Both models benefit: Linear Regression because the
assumptions of ordinary least squares are closer to being satisfied, and GBT
because the residuals in early boosting rounds are smaller, which leads to more
stable trees.
 
All metrics (RMSE, R²) are reported on this log scale. The `exp(RMSE)` sanity
check at the end of each run translates the error back into a multiplier on the
original stream count.
 
### 2. Log-transforming `artist_avg_past_streams`
 
The Stage 2 EDA momentum table showed that artists whose past releases averaged
5 M+ streams produced about 19 M first-month streams on their next release,
compared to 2.7 M for the 1 M–5 M tier — a 7× jump at a single tier boundary.
Feeding the raw values into a linear model would distort the gradient in the
same way as an untransformed target. The `LogTransformer` handles this as a
proper pipeline stage rather than an ad-hoc preprocessing step, which keeps the
transformation documented and reproducible.
 
### 3. Feature set: all 32 features kept
 
No features were manually removed. GBT handles irrelevant features gracefully
via its internal splitting criterion, and Linear Regression with ElasticNet
regularization can shrink unimportant coefficients toward zero. Dropping
features without a formal selection method would require separate justification.
 
### 4. Train / test split: 80/20, fixed seed
 
We split 80% for training and 20% for testing with `seed=42`. With roughly
87 500 rows the test set contains around 17 500 samples — large enough for
stable metric estimates. The fixed seed makes results reproducible across runs.
 
### 5. StandardScaler for Linear Regression only
 
The 32 features span very different scales: `duration_ms` in the hundreds of
thousands, audio scores in 0–1, binary market flags at 0 or 1. Without
scaling, gradient descent assigns distorted weights based on feature magnitude
rather than predictive power. We apply `StandardScaler(withStd=True,
withMean=True)` inside the LR `Pipeline`, fitted only on training data so that
test-set statistics never influence the model.
 
GBT does not require scaling. Tree split thresholds are invariant to monotonic
transformations of feature values.
 
### 6. Cross-validated grid search for both models
 
Each model is tuned using 3-fold cross-validation on training data only. The
test set is not touched during tuning. RMSE is the optimization metric.
`parallelism=5` reduces wall-clock time on the cluster.
 
| Model | Grid | Configs | Folds | Total fits |
|-------|------|---------|-------|------------|
| Linear Regression | 3 × `regParam`, 3 × `elasticNetParam` | 9 | 3 | 27 |
| GBT | 2 × `maxDepth`, 2 × `stepSize` | 4 | 3 | 12 |
 
The best hyperparameter set is selected from CV results and the final model is
evaluated once on the held-out test set.
 
---
 
## Results
 
All metrics are on the log1p-transformed target.
 
| Model | RMSE | R² | exp(RMSE) |
|-------|------|----|-----------|
| Linear Regression | **2.1650** | **0.1587** | **≈ 8.71×** |
| GBT Regressor | **1.8661** | **0.3749** | **≈ 6.46×** |
 
GBT outperforms Linear Regression on both metrics. The RMSE difference of 0.30
on the log scale corresponds to approximately a 1.35× improvement in the
average prediction error on raw stream counts.
 
Best hyperparameters selected by cross-validation:
 
| Model | Hyperparameter | Best value |
|-------|---------------|------------|
| Linear Regression | `regParam` | 0.001 |
| Linear Regression | `elasticNetParam` | 0.5 |
| GBT | `maxDepth` | 5 |
| GBT | `stepSize` | 0.1 |
 
### Why are R² values modest?
 
An R² of 0.37 for GBT is the realistic ceiling for this problem given the
available features. A large share of a song's streaming performance is driven
by factors that are not observable before release: Spotify playlist placement,
editorial features, social media virality, and press coverage. None of these
are captured in audio features or pre-release metadata.
 
The features we have explain the structural component of popularity — artist
track record, market availability, release timing, and sonic characteristics.
The remaining variance is genuine uncertainty. R² values in the 0.30–0.40 range
are consistent with published results on pre-release stream prediction tasks.
 
---
 
## What the Models Learned
 
GBT feature importances from the run confirm the pattern suggested by Stage 2 EDA:
 
| Rank | Feature | Importance |
|------|---------|------------|
| 1 | `log_artist_avg_past_streams` | 0.4294 |
| 2 | `artist_prior_tracks_count` | 0.0925 |
| 3 | `af_loudness` | 0.0640 |
| 4 | `af_instrumentalness` | 0.0559 |
| 5 | `af_danceability` | 0.0369 |
| 6 | `num_markets` | 0.0368 |
| 7 | `explicit` | 0.0319 |
| 8 | `duration_ms` | 0.0307 |
| 9 | `af_acousticness` | 0.0304 |
| 10 | `release_day_of_week_sin` | 0.0300 |
 
`log_artist_avg_past_streams` alone accounts for 43% of total importance,
confirming that past streaming performance is the dominant predictor of future
performance. Artist history features (ranks 1–2) together account for over 52%
of total importance.
 
The appearance of `release_day_of_week_sin` in the top 10 is consistent with
the Stage 2 EDA finding that Friday releases average 3.27 M first-month streams
versus 979 K for Monday releases. The sin encoding correctly captures the
proximity of Friday (6) and Saturday (7) to Sunday (1) in the weekly cycle.
 
Audio features contributed but ranked lower than artist and distribution
features. This is the main finding of the modeling stage: for pre-release
stream prediction, *who* releases a song and *when* matters more than *what the
song sounds like*.
