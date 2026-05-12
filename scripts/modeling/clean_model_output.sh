#!/bin/bash

echo "==========================================="
echo "Cleaning Stage III artifacts..."
echo "==========================================="

echo "1/2: Removing local Stage 3 files..."

rm -f data/train.json
rm -f data/test.json

rm -rf models/model1
rm -rf models/model2

rm -f output/model1_predictions.csv
rm -f output/model2_predictions.csv
rm -f output/evaluation.csv
rm -f output/pylint_stage3.txt

# 2. Clean HDFS Files
echo "2/2: Removing HDFS Stage 3 files..."

hdfs dfs -rm -r -f project/data/train
hdfs dfs -rm -r -f project/data/test

hdfs dfs -rm -r -f project/models/model1
hdfs dfs -rm -r -f project/models/model2

hdfs dfs -rm -r -f project/output/model1_predictions.csv
hdfs dfs -rm -r -f project/output/model2_predictions.csv
hdfs dfs -rm -r -f project/output/evaluation.csv

echo "==========================================="
echo "Cleanup complete! Your Stage I and II data is untouched."
echo "You can now safely re-run scripts/stage3.sh."
echo "==========================================="