USE team23_projectdb;
SET hive.execution.engine=mr;
SET hive.resultset.use.unique.column.names=false;

DROP TABLE IF EXISTS q3_results;

CREATE EXTERNAL TABLE q3_results (
    reach_rank INT,
    market_reach_tier STRING,
    track_count BIGINT,
    avg_first_month_streams DOUBLE
)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
STORED AS TEXTFILE
LOCATION 'project/hive/warehouse/q3_results';

INSERT OVERWRITE TABLE q3_results
SELECT
    reach_rank,
    market_reach_tier,
    COUNT(*) AS track_count,
    ROUND(AVG(streams_in_the_first_month), 2) AS avg_first_month_streams
FROM (
    SELECT
        CASE
            WHEN num_markets < 50 THEN 1
            WHEN num_markets BETWEEN 50 AND 99 THEN 2
            WHEN num_markets BETWEEN 100 AND 149 THEN 3
            WHEN num_markets BETWEEN 150 AND 199 THEN 4
            ELSE 5
        END AS reach_rank,
        CASE
            WHEN num_markets < 50 THEN '<50 markets'
            WHEN num_markets BETWEEN 50 AND 99 THEN '50-99 markets'
            WHEN num_markets BETWEEN 100 AND 149 THEN '100-149 markets'
            WHEN num_markets BETWEEN 150 AND 199 THEN '150-199 markets'
            ELSE '200+ markets'
        END AS market_reach_tier,
        streams_in_the_first_month
    FROM songs_part_bucket
) t
GROUP BY reach_rank, market_reach_tier
ORDER BY reach_rank
LIMIT 100;

INSERT OVERWRITE DIRECTORY 'project/output/q3_market_reach'
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
SELECT * FROM q3_results;