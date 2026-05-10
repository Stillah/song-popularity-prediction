USE team23_projectdb;
SET hive.resultset.use.unique.column.names=false;

DROP TABLE IF EXISTS stage4_market_availability_map;

CREATE EXTERNAL TABLE stage4_market_availability_map (
    market_code STRING,
    market_name STRING,
    latitude DOUBLE,
    longitude DOUBLE,
    available_tracks BIGINT,
    availability_pct DOUBLE,
    avg_first_month_streams DOUBLE
)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
STORED AS TEXTFILE
LOCATION 'project/stage4/market_availability_map'
TBLPROPERTIES ('skip.header.line.count'='1');

SELECT * FROM stage4_market_availability_map;
