USE team23_projectdb;
SET hive.execution.engine=mr;
SET hive.resultset.use.unique.column.names=false;

DROP TABLE IF EXISTS q4_results;

CREATE EXTERNAL TABLE q4_results (
    maturity_rank INT,
    artist_maturity_tier STRING,
    track_count BIGINT,
    avg_first_month_streams DOUBLE
)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
STORED AS TEXTFILE
LOCATION 'project/hive/warehouse/q4_results';

INSERT OVERWRITE TABLE q4_results
SELECT
    maturity_rank,
    artist_maturity_tier,
    COUNT(*) AS track_count,
    ROUND(AVG(streams_in_the_first_month), 2) AS avg_first_month_streams
FROM (
    SELECT
        CASE
            WHEN artist_prior_tracks_count = 0 THEN 1
            WHEN artist_prior_tracks_count BETWEEN 1 AND 4 THEN 2
            WHEN artist_prior_tracks_count BETWEEN 5 AND 19 THEN 3
            ELSE 4
        END AS maturity_rank,
        CASE
            WHEN artist_prior_tracks_count = 0 THEN '0 prior tracks (debut)'
            WHEN artist_prior_tracks_count BETWEEN 1 AND 4 THEN '1-4 prior tracks'
            WHEN artist_prior_tracks_count BETWEEN 5 AND 19 THEN '5-19 prior tracks'
            ELSE '20+ prior tracks'
        END AS artist_maturity_tier,
        streams_in_the_first_month
    FROM songs_part_bucket
) t
GROUP BY maturity_rank, artist_maturity_tier
ORDER BY maturity_rank
LIMIT 100;

INSERT OVERWRITE DIRECTORY 'project/output/q4_artist_maturity'
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
SELECT * FROM q4_results;