USE team23_projectdb;
SET hive.execution.engine=mr;
SET hive.resultset.use.unique.column.names=false;

DROP TABLE IF EXISTS q1_results;

CREATE EXTERNAL TABLE q1_results (
    release_month INT,
    track_count BIGINT,
    avg_first_month_streams DOUBLE
)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
STORED AS TEXTFILE
LOCATION 'project/hive/warehouse/q1_results';

INSERT OVERWRITE TABLE q1_results
SELECT
    release_month,
    COUNT(*) AS track_count,
    ROUND(AVG(streams_in_the_first_month), 2) AS avg_first_month_streams
FROM songs_part_bucket
GROUP BY release_month
ORDER BY release_month
LIMIT 100;

INSERT OVERWRITE DIRECTORY 'project/output/q1_release_month'
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
SELECT * FROM q1_results;