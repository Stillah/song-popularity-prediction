USE team23_projectdb;
SET hive.execution.engine=mr;
SET hive.resultset.use.unique.column.names=false;

DROP TABLE IF EXISTS q0_results;

CREATE EXTERNAL TABLE q0_results (
    metric STRING,
    value STRING
)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
STORED AS TEXTFILE
LOCATION 'project/hive/warehouse/q0_results';

INSERT OVERWRITE TABLE q0_results
SELECT 'row_count', CAST(COUNT(*) AS STRING) FROM songs_part_bucket
UNION ALL
SELECT 'model_feature_count', '29'
UNION ALL
SELECT 'target_column_count', '1'
UNION ALL
SELECT 'technical_column_count', '1'
UNION ALL
SELECT 'partition_column', 'release_month'
UNION ALL
SELECT 'bucket_column', 'num_markets'
UNION ALL
SELECT 'min_streams_in_first_month', CAST(MIN(streams_in_the_first_month) AS STRING) FROM songs_part_bucket
UNION ALL
SELECT 'avg_streams_in_first_month', CAST(ROUND(AVG(streams_in_the_first_month), 2) AS STRING) FROM songs_part_bucket
UNION ALL
SELECT 'max_streams_in_first_month', CAST(MAX(streams_in_the_first_month) AS STRING) FROM songs_part_bucket
UNION ALL
SELECT 'avg_num_markets', CAST(ROUND(AVG(num_markets), 2) AS STRING) FROM songs_part_bucket
UNION ALL
SELECT 'explicit_share_pct',
       CAST(ROUND(100.0 * AVG(CAST(explicit AS DOUBLE)), 2) AS STRING)
FROM songs_part_bucket;

INSERT OVERWRITE DIRECTORY 'project/output/q0_characteristics'
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
SELECT * FROM q0_results;