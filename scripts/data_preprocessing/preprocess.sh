#!/bin/bash

set -e

echo "Starting Data Downloading and Preprocessing"

echo "Activating virtual environment..."
source venv/bin/activate

bash scripts/data_preprocessing/data_collection.sh

echo "Cleaning local data/clean_data/ directory..."
rm -rf data/clean_data
mkdir -p data/clean_data

HDFS_RAW_DIR="/user/team23/raw_data"
HDFS_CLEAN_DIR="/user/team23/clean_data"
HDFS_TRASH="/user/team23/.Trash"

echo "Checking if raw data exists in HDFS at $HDFS_RAW_DIR..."

if hdfs dfs -test -e $HDFS_RAW_DIR/merged_data.csv; then
    echo "Raw data already exists in HDFS. Skipping copy."
else
    echo "Copying raw data from local to HDFS. This may take a while..."
    hdfs dfs -mkdir -p $HDFS_RAW_DIR
    hdfs dfs -Ddfs.replication=1 -put data/merged_data.csv $HDFS_RAW_DIR/
    echo "Raw data copied to HDFS."
fi

echo "Cleaning HDFS clean data directory at $HDFS_CLEAN_DIR..."
hdfs dfs -rm -r -f $HDFS_CLEAN_DIR
hdfs dfs -rm -r -f $HDFS_TRASH
hdfs dfs -mkdir $HDFS_TRASH

echo "Running preprocess.py with Spark..."
spark-submit \
    --master yarn \
    --deploy-mode client \
    --driver-memory 4g \
    --executor-memory 4g \
    --num-executors 4 \
    scripts/data_preprocessing/preprocess.py
    
echo "Copying clean data from HDFS to local data/clean_data/..."
hdfs dfs -get $HDFS_CLEAN_DIR/* data/clean_data/

echo "Cleaning up HDFS..."
hdfs dfs -rm -r -f $HDFS_RAW_DIR
hdfs dfs -rm -r -f $HDFS_CLEAN_DIR

echo "Clean data saved to data/clean_data/"