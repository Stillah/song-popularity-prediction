#!/bin/bash

set -e

echo "Sqoop Import: PostgreSQL to HDFS"

password=$(head -n 1 secrets/.psql.pass)

echo "Cleaning HDFS directory /user/team23/project/warehouse..."
hdfs dfs -rm -r -f /user/team23/project/warehouse

echo "Importing table 'songs' from PostgreSQL to HDFS..."
sqoop import \
    --connect jdbc:postgresql://hadoop-04.uni.innopolis.ru/team23_projectdb \
    --username team23 \
    --password $password \
    --table songs \
    --target-dir /user/team23/project/warehouse \
    --as-parquetfile \
    --compress \
    --compression-codec snappy \
    --split-by id \
    -m 4

echo "Sqoop Import completed"