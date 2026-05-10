USE team23_projectdb;
SET hive.resultset.use.unique.column.names=false;

DROP TABLE IF EXISTS stage4_ml_model_comparison;
DROP TABLE IF EXISTS stage4_ml_prediction_samples;
DROP TABLE IF EXISTS stage4_ml_hyperparameters;
DROP TABLE IF EXISTS stage4_ml_feature_summary;
DROP TABLE IF EXISTS stage4_ml_feature_importance;

CREATE EXTERNAL TABLE stage4_ml_model_comparison (
    model STRING,
    rmse DOUBLE,
    mae DOUBLE,
    r2 DOUBLE,
    exp_mae DOUBLE,
    interpretation STRING
)

ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
STORED AS TEXTFILE
LOCATION 'project/stage4/ml_model_comparison'
TBLPROPERTIES ('skip.header.line.count'='1');

CREATE EXTERNAL TABLE stage4_ml_prediction_samples (
    sample_id INT,
    model STRING,
    actual_log_streams DOUBLE,
    predicted_log_streams DOUBLE,
    actual_streams_est BIGINT,
    predicted_streams_est BIGINT,
    abs_error_log DOUBLE
)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
STORED AS TEXTFILE
LOCATION 'project/stage4/ml_prediction_samples'
TBLPROPERTIES ('skip.header.line.count'='1');

CREATE EXTERNAL TABLE stage4_ml_hyperparameters (
    model STRING,
    hyperparameter STRING,
    searched_values STRING,
    best_value STRING,
    reason STRING
)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
STORED AS TEXTFILE
LOCATION 'project/stage4/ml_hyperparameters'
TBLPROPERTIES ('skip.header.line.count'='1');

CREATE EXTERNAL TABLE stage4_ml_feature_summary (
    feature_group STRING,
    feature_name STRING,
    description STRING,
    used_in_model STRING
)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
STORED AS TEXTFILE
LOCATION 'project/stage4/ml_feature_summary'
TBLPROPERTIES ('skip.header.line.count'='1');

CREATE EXTERNAL TABLE stage4_ml_feature_importance (
    rank INT,
    feature STRING,
    importance DOUBLE,
    model STRING
)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
STORED AS TEXTFILE
LOCATION 'project/stage4/ml_feature_importance'
TBLPROPERTIES ('skip.header.line.count'='1');

SELECT * FROM stage4_ml_model_comparison;
SELECT * FROM stage4_ml_prediction_samples LIMIT 10;
SELECT * FROM stage4_ml_hyperparameters;
SELECT * FROM stage4_ml_feature_summary LIMIT 10;
SELECT * FROM stage4_ml_feature_importance;
