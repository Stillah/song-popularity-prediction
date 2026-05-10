USE team23_projectdb;
SET hive.execution.engine=mr;
SET hive.resultset.use.unique.column.names=false;

DROP TABLE IF EXISTS q2_results;

CREATE EXTERNAL TABLE q2_results (
    weekday_num INT,
    release_weekday STRING,
    track_count BIGINT,
    avg_first_month_streams DOUBLE
)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
STORED AS TEXTFILE
LOCATION 'project/hive/warehouse/q2_results';

INSERT OVERWRITE TABLE q2_results
SELECT
    release_day_of_week AS weekday_num,
    CASE release_day_of_week
        WHEN 1 THEN 'Sunday'
        WHEN 2 THEN 'Monday'
        WHEN 3 THEN 'Tuesday'
        WHEN 4 THEN 'Wednesday'
        WHEN 5 THEN 'Thursday'
        WHEN 6 THEN 'Friday'
        WHEN 7 THEN 'Saturday'
    END AS release_weekday,
    COUNT(*) AS track_count,
    ROUND(AVG(streams_in_the_first_month), 2) AS avg_first_month_streams
FROM songs_part_bucket
GROUP BY release_day_of_week
ORDER BY weekday_num
LIMIT 100;

INSERT OVERWRITE DIRECTORY 'project/output/q2_release_weekday'
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
SELECT * FROM q2_results;