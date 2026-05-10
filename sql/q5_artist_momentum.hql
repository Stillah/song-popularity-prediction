USE team23_projectdb;
SET hive.execution.engine=mr;
SET hive.resultset.use.unique.column.names=false;

DROP TABLE IF EXISTS q5_results;

CREATE EXTERNAL TABLE q5_results (
    momentum_rank INT,
    artist_history_strength STRING,
    track_count BIGINT,
    avg_first_month_streams DOUBLE
)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
STORED AS TEXTFILE
LOCATION 'project/hive/warehouse/q5_results';

INSERT OVERWRITE TABLE q5_results
SELECT
    momentum_rank,
    artist_history_strength,
    COUNT(*) AS track_count,
    ROUND(AVG(streams_in_the_first_month), 2) AS avg_first_month_streams
FROM (
    SELECT
        CASE
            WHEN artist_avg_past_streams = 0 THEN 1
            WHEN artist_avg_past_streams > 0 AND artist_avg_past_streams < 100000 THEN 2
            WHEN artist_avg_past_streams >= 100000 AND artist_avg_past_streams < 500000 THEN 3
            WHEN artist_avg_past_streams >= 500000 AND artist_avg_past_streams < 1000000 THEN 4
            WHEN artist_avg_past_streams >= 1000000 AND artist_avg_past_streams < 5000000 THEN 5
            ELSE 6
        END AS momentum_rank,
        CASE
            WHEN artist_avg_past_streams = 0 THEN '0 past avg streams'
            WHEN artist_avg_past_streams > 0 AND artist_avg_past_streams < 100000 THEN '0-100K'
            WHEN artist_avg_past_streams >= 100000 AND artist_avg_past_streams < 500000 THEN '100K-500K'
            WHEN artist_avg_past_streams >= 500000 AND artist_avg_past_streams < 1000000 THEN '500K-1M'
            WHEN artist_avg_past_streams >= 1000000 AND artist_avg_past_streams < 5000000 THEN '1M-5M'
            ELSE '5M+'
        END AS artist_history_strength,
        streams_in_the_first_month
    FROM songs_part_bucket
) t
GROUP BY momentum_rank, artist_history_strength
ORDER BY momentum_rank
LIMIT 100;

INSERT OVERWRITE DIRECTORY 'project/output/q5_artist_momentum'
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
SELECT * FROM q5_results;