#!/bin/bash
set -e

QUERY_NAME="$1"
HEADER="$2"

if [ -z "$QUERY_NAME" ] || [ -z "$HEADER" ]; then
    echo "Usage: bash scripts/run_hive_query.sh <query_name_without_extension> <csv_header>"
    exit 1
fi

mkdir -p output

password=$(head -n 1 secrets/.hive.pass)

echo "Running ${QUERY_NAME}..."

hdfs dfs -rm -r -f "/user/team23/project/output/${QUERY_NAME}" || true

beeline -u jdbc:hive2://hadoop-03.uni.innopolis.ru:10001 \
  -n team23 \
  -p "$password" \
  -f "sql/${QUERY_NAME}.hql" \
  > "output/${QUERY_NAME}_beeline.txt" 2>&1

echo "$HEADER" > "output/${QUERY_NAME}.csv"
hdfs dfs -cat "project/output/${QUERY_NAME}/*" >> "output/${QUERY_NAME}.csv"

echo "${QUERY_NAME} exported to output/${QUERY_NAME}.csv"