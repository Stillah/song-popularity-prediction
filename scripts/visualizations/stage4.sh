#!/bin/bash
set -euo pipefail

echo "Stage IV: Presentation dashboard preparation"

mkdir -p output/stage4

echo "[1/5] Preparing dashboard CSV files..."
python3 scripts/visualizations/prepare_stage4.py

echo "[2/5] Uploading Stage IV CSV files to HDFS..."
hdfs dfs -rm -r -f project/stage4/ml_model_comparison
hdfs dfs -rm -r -f project/stage4/ml_prediction_samples
hdfs dfs -rm -r -f project/stage4/ml_hyperparameters
hdfs dfs -rm -r -f project/stage4/ml_feature_summary
hdfs dfs -rm -r -f project/stage4/ml_feature_importance
hdfs dfs -mkdir -p project/stage4/ml_model_comparison
hdfs dfs -mkdir -p project/stage4/ml_prediction_samples
hdfs dfs -mkdir -p project/stage4/ml_hyperparameters
hdfs dfs -mkdir -p project/stage4/ml_feature_summary
hdfs dfs -mkdir -p project/stage4/ml_feature_importance
hdfs dfs -put -f output/stage4/ml_model_comparison.csv project/stage4/ml_model_comparison/
hdfs dfs -put -f output/stage4/ml_prediction_samples.csv project/stage4/ml_prediction_samples/
hdfs dfs -put -f output/stage4/ml_hyperparameters.csv project/stage4/ml_hyperparameters/
hdfs dfs -put -f output/stage4/ml_feature_summary.csv project/stage4/ml_feature_summary/
hdfs dfs -put -f output/stage4/ml_feature_importance.csv project/stage4/ml_feature_importance/

echo "[3/5] Creating Hive tables for Superset..."
password=$(head -n 1 secrets/.hive.pass)
set +e
beeline \
    -u jdbc:hive2://hadoop-03.uni.innopolis.ru:10001 \
    -n team23 \
    -p "$password" \
    -f sql/stage4_ml_tables.hql \
    > output/stage4_hive_tables.txt 2>&1
beeline_status=$?
set -e

if [ "$beeline_status" -ne 0 ]; then
    echo "Hive table creation failed. Check output/stage4_hive_tables.txt"
    echo "The CSV files were uploaded to HDFS, so sql/stage4_ml_tables.hql can be rerun later."
else
    echo "Hive tables for Stage IV were created."
fi

echo "[4/5] Validating Stage IV outputs..."
python3 scripts/code_style/validate_stage4.py 2>&1 | tee output/validate_stage4.txt

echo "[5/5] Running pylint for Stage IV scripts..."
bash scripts/code_style/pylint_stage4.sh
