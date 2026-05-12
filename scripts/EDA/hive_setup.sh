#!/bin/bash
set -e

echo "Hive setup"

mkdir -p output

password=$(head -n 1 secrets/.hive.pass)

echo "Cleaning HDFS folders..."
hdfs dfs -rm -r -f /user/team23/project/hive/warehouse || true
hdfs dfs -rm -r -f /user/team23/project/output || true

echo "Running Hive DDL..."
beeline -u jdbc:hive2://hadoop-03.uni.innopolis.ru:10001 \
  -n team23 \
  -p "$password" \
  -f sql/db.hql \
  > output/db_setup.txt 2>&1

echo "Saving HDFS listing..."
hdfs dfs -ls -R /user/team23/project/hive/warehouse > output/hive_warehouse_ls.txt 2>&1 || true

echo "Hive setup completed"