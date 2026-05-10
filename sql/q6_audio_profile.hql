USE team23_projectdb;
SET hive.execution.engine=mr;
SET hive.resultset.use.unique.column.names=false;

DROP TABLE IF EXISTS q6_results;

CREATE EXTERNAL TABLE q6_results (
    profile_rank INT,
    audio_profile STRING,
    track_count BIGINT,
    avg_first_month_streams DOUBLE
)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
STORED AS TEXTFILE
LOCATION 'project/hive/warehouse/q6_results';

INSERT OVERWRITE TABLE q6_results
SELECT
    profile_rank,
    audio_profile,
    COUNT(*) AS track_count,
    ROUND(AVG(streams_in_the_first_month), 2) AS avg_first_month_streams
FROM (
    SELECT
        CASE
            WHEN af_danceability >= 0.6 AND af_energy >= 0.6 THEN 1
            WHEN af_danceability >= 0.6 AND af_energy < 0.6 THEN 2
            WHEN af_danceability < 0.6 AND af_energy >= 0.6 THEN 3
            ELSE 4
        END AS profile_rank,
        CASE
            WHEN af_danceability >= 0.6 AND af_energy >= 0.6 THEN 'High danceability / High energy'
            WHEN af_danceability >= 0.6 AND af_energy < 0.6 THEN 'High danceability / Low energy'
            WHEN af_danceability < 0.6 AND af_energy >= 0.6 THEN 'Low danceability / High energy'
            ELSE 'Low danceability / Low energy'
        END AS audio_profile,
        streams_in_the_first_month
    FROM songs_part_bucket
) t
GROUP BY profile_rank, audio_profile
ORDER BY profile_rank
LIMIT 100;

INSERT OVERWRITE DIRECTORY 'project/output/q6_audio_profile'
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
SELECT * FROM q6_results;