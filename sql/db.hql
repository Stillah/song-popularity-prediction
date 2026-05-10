SET hive.execution.engine=tez;
SET hive.exec.dynamic.partition=true;
SET hive.exec.dynamic.partition.mode=nonstrict;
SET hive.enforce.bucketing=true;
SET parquet.compression=SNAPPY;
SET hive.resultset.use.unique.column.names=false;

DROP DATABASE IF EXISTS team23_projectdb CASCADE;
CREATE DATABASE team23_projectdb LOCATION 'project/hive/warehouse';
USE team23_projectdb;

-- Base external table over Stage I Sqoop output.
-- IMPORTANT:
-- Your current sqoop_import.sh uses:
--   --target-dir /user/team23/project/warehouse
-- so the Parquet files are directly in project/warehouse
-- and not inside project/warehouse/songs

DROP TABLE IF EXISTS songs_ext;

CREATE EXTERNAL TABLE songs_ext (
    id BIGINT,
    af_danceability DOUBLE,
    af_energy DOUBLE,
    key_sin DOUBLE,
    key_cos DOUBLE,
    af_loudness DOUBLE,
    af_mode INT,
    af_speechiness DOUBLE,
    af_acousticness DOUBLE,
    af_instrumentalness DOUBLE,
    af_liveness DOUBLE,
    af_valence DOUBLE,
    af_tempo DOUBLE,
    af_time_signature DOUBLE,
    duration_ms DOUBLE,
    explicit INT,
    release_month INT,
    release_day_of_week INT,
    release_week_of_year INT,
    num_markets INT,
    is_market_us INT,
    is_market_gb INT,
    is_market_de INT,
    is_market_jp INT,
    is_market_br INT,
    is_market_fr INT,
    is_market_ca INT,
    is_market_au INT,
    artist_prior_tracks_count INT,
    artist_avg_past_streams DOUBLE,
    streams_in_the_first_month DOUBLE
)
STORED AS PARQUET
LOCATION 'project/warehouse';

-- Quick checks on the raw external table
SELECT COUNT(*) AS songs_ext_count FROM songs_ext;
DESCRIBE FORMATTED songs_ext;

DROP TABLE IF EXISTS songs_part_bucket;

CREATE EXTERNAL TABLE songs_part_bucket (
    id BIGINT,
    af_danceability DOUBLE,
    af_energy DOUBLE,
    key_sin DOUBLE,
    key_cos DOUBLE,
    af_loudness DOUBLE,
    af_mode INT,
    af_speechiness DOUBLE,
    af_acousticness DOUBLE,
    af_instrumentalness DOUBLE,
    af_liveness DOUBLE,
    af_valence DOUBLE,
    af_tempo DOUBLE,
    af_time_signature DOUBLE,
    duration_ms DOUBLE,
    explicit INT,
    release_day_of_week INT,
    release_week_of_year INT,
    num_markets INT,
    is_market_us INT,
    is_market_gb INT,
    is_market_de INT,
    is_market_jp INT,
    is_market_br INT,
    is_market_fr INT,
    is_market_ca INT,
    is_market_au INT,
    artist_prior_tracks_count INT,
    artist_avg_past_streams DOUBLE,
    streams_in_the_first_month DOUBLE
)
PARTITIONED BY (release_month INT)
CLUSTERED BY (num_markets) INTO 16 BUCKETS
STORED AS PARQUET
LOCATION 'project/hive/warehouse/songs_part_bucket';

-- Dynamic partition insert:
-- partition column must be last in SELECT
INSERT OVERWRITE TABLE songs_part_bucket PARTITION (release_month)
SELECT
    id,
    af_danceability,
    af_energy,
    key_sin,
    key_cos,
    af_loudness,
    af_mode,
    af_speechiness,
    af_acousticness,
    af_instrumentalness,
    af_liveness,
    af_valence,
    af_tempo,
    af_time_signature,
    duration_ms,
    explicit,
    release_day_of_week,
    release_week_of_year,
    num_markets,
    is_market_us,
    is_market_gb,
    is_market_de,
    is_market_jp,
    is_market_br,
    is_market_fr,
    is_market_ca,
    is_market_au,
    artist_prior_tracks_count,
    artist_avg_past_streams,
    streams_in_the_first_month,
    release_month
FROM songs_ext;

MSCK REPAIR TABLE songs_part_bucket;

SELECT COUNT(*) AS songs_part_bucket_count FROM songs_part_bucket;
SHOW PARTITIONS songs_part_bucket;
DESCRIBE FORMATTED songs_part_bucket;

-- Stage II checklist says EDA should use optimized tables, not the plain unpartitioned one
DROP TABLE IF EXISTS songs_ext;

SHOW TABLES;
SELECT release_month, COUNT(*) AS cnt
FROM songs_part_bucket
GROUP BY release_month
ORDER BY release_month
LIMIT 100;