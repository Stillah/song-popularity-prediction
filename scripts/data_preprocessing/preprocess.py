#!/usr/bin/env python3
"""Preprocess Spotify charts data for song popularity prediction."""

import math
from pyspark.sql import SparkSession
from pyspark.sql.functions import (
    col, to_date, datediff, sum as spark_sum,
    when, lit, count, avg, sin, cos, regexp_replace, split,
    size, array_contains, row_number, weekofyear, dayofweek
)
from pyspark.sql.types import IntegerType
from pyspark.sql.window import Window


def convert_explicit(df):  # pylint: disable=invalid-name
    """Convert explicit column from string/boolean to integer (0/1)."""
    return df.withColumn(
        "explicit",
        when(col("explicit") == "", lit(0))
        .when(col("explicit") == "True", lit(1))
        .when(col("explicit") == "False", lit(0))
        .otherwise(col("explicit").cast(IntegerType()))
    )


def compute_target(df):  # pylint: disable=invalid-name
    """Compute streams_in_the_first_month for each track."""
    df_with_days = df.withColumn(
        "days_since_release",
        datediff(col("date_parsed"), col("release_date_parsed"))
    )
    df_first_30 = df_with_days.filter(
        (col("days_since_release") >= 0) & (col("days_since_release") <= 29)
    )
    return df_first_30.groupBy("track_id").agg(
        spark_sum("streams").alias("streams_in_the_first_month")
    )


def get_distinct_tracks(df):  # pylint: disable=invalid-name
    """Get one row per track_id (first occurrence by date)."""
    window_spec = Window.partitionBy("track_id").orderBy("date_parsed")
    return df.withColumn("rn", row_number().over(window_spec)) \
        .filter(col("rn") == 1) \
        .drop("rn")


def extract_seasonal_features(df):  # pylint: disable=invalid-name
    """Extract month, day of week, and week of year from release_date."""
    return df \
        .withColumn(
            "release_month",
            col("release_date_parsed").substr(6, 2).cast(IntegerType())
        ) \
        .withColumn(
            "release_day_of_week",
            dayofweek(col("release_date_parsed")).cast(IntegerType())
        ) \
        .withColumn(
            "release_week_of_year",
            weekofyear(col("release_date_parsed")).cast(IntegerType())
        )


def parse_available_markets(df):  # pylint: disable=invalid-name
    """Parse available_markets string into num_markets and 8 binary flags."""
    top_markets = ["US", "GB", "DE", "JP", "BR", "FR", "CA", "AU"]

    df_clean = df.withColumn(
        "markets_clean",
        regexp_replace(col("available_markets"), "[\\[\\]']", "")
    )
    df_clean = df_clean.withColumn(
        "markets_array",
        split(col("markets_clean"), ", ")
    )
    df_clean = df_clean.withColumn(
        "num_markets",
        size(col("markets_array"))
    )

    for market in top_markets:
        df_clean = df_clean.withColumn(
            f"is_market_{market.lower()}",
            when(array_contains(col("markets_array"), market), 1).otherwise(0)
        )

    return df_clean.drop("markets_clean", "markets_array")


def cyclical_encode_key(df):  # pylint: disable=invalid-name
    """Apply cyclical encoding (sin/cos) to af_key."""
    return df \
        .withColumn(
            "key_sin",
            sin(2 * math.pi * col("af_key") / 12)
        ) \
        .withColumn(
            "key_cos",
            cos(2 * math.pi * col("af_key") / 12)
        )


def compute_artist_history(df_final):
    """Compute artist prior tracks count and avg past streams (no leakage)."""
    artist_tracks = df_final.select(
        "artist", "track_id", "release_date_parsed", "streams_in_the_first_month"
    )
    window_artist = Window.partitionBy("artist").orderBy("release_date_parsed")

    artist_tracks = artist_tracks.withColumn(
        "artist_prior_tracks_count",
        count("track_id").over(
            window_artist.rowsBetween(Window.unboundedPreceding, -1)
        )
    )
    artist_tracks = artist_tracks.withColumn(
        "artist_avg_past_streams",
        avg("streams_in_the_first_month").over(
            window_artist.rowsBetween(Window.unboundedPreceding, -1)
        )
    )

    artist_tracks = artist_tracks.fillna({
        "artist_prior_tracks_count": 0,
        "artist_avg_past_streams": 0.0
    })

    return artist_tracks.select(
        "track_id", "artist_prior_tracks_count", "artist_avg_past_streams"
    )


def main():
    """Main function: orchestrate preprocessing pipeline."""
    spark = SparkSession.builder \
        .appName("SpotifyPopularityPreprocessing") \
        .config("spark.sql.adaptive.enabled", "true") \
        .config("spark.sql.adaptive.coalescePartitions.enabled", "true") \
        .config("spark.sql.legacy.timeParserPolicy", "LEGACY") \
        .getOrCreate()

    spark.sparkContext.setLogLevel("WARN")

    logger = spark._jvm.org.apache.log4j.LogManager.getLogger(__name__)  # pylint: disable=protected-access
    logger.info("Starting Spotify data preprocessing")

    logger.info("Reading raw data from\
                /user/team23/raw_data/merged_data.csv...")
    df_merged = spark.read \
        .option("header", "true") \
        .option("inferSchema", "true") \
        .option("nullValue", "NULL") \
        .csv("/user/team23/raw_data/merged_data.csv")

    logger.info(f"Raw data loaded: {df_merged.count()} rows, {len(df_merged.columns)} columns")

    logger.info("Filtering: keeping only top200 charts...")
    df_merged = df_merged.filter(col("chart") == "top200")
    logger.info(f"After filtering: {df_merged.count()} rows")

    logger.info("Converting explicit column...")
    df_merged = convert_explicit(df_merged)

    logger.info("Converting af_mode to integer...")
    df_merged = df_merged.withColumn("af_mode", col("af_mode").cast(IntegerType()))

    logger.info("Converting date columns...")
    df_merged = df_merged.withColumn(
        "release_date_parsed", to_date(col("release_date"), "yyyy-MM-dd")
    )
    df_merged = df_merged.withColumn(
        "date_parsed", to_date(col("date"), "yyyy-MM-dd")
    )

    logger.info("Computing target variable...")
    df_target = compute_target(df_merged)

    logger.info("Getting distinct tracks...")
    df_distinct = get_distinct_tracks(df_merged)
    logger.info(f"Distinct tracks: {df_distinct.count()}")

    logger.info("Extracting seasonal features...")
    df_features = extract_seasonal_features(df_distinct)

    logger.info("Parsing available markets...")
    df_features = parse_available_markets(df_features)

    logger.info("Cyclical encoding of af_key...")
    df_features = cyclical_encode_key(df_features)

    logger.info("Joining target variable...")
    df_final = df_features.join(df_target, on="track_id", how="inner")

    logger.info("Computing artist history features...")
    artist_features = compute_artist_history(df_final)
    df_final = df_final.join(artist_features, on="track_id", how="inner")

    # Sort by release date ascending to enable time-based splitting downstream
    df_final = df_final.orderBy("release_date_parsed")

    final_columns = [
        "af_danceability", "af_energy", "key_sin", "key_cos",
        "af_loudness", "af_mode", "af_speechiness", "af_acousticness",
        "af_instrumentalness", "af_liveness", "af_valence", "af_tempo",
        "af_time_signature", "duration_ms", "explicit", "release_month",
        "release_day_of_week", "release_week_of_year", "num_markets",
        "is_market_us", "is_market_gb", "is_market_de", "is_market_jp",
        "is_market_br", "is_market_fr", "is_market_ca", "is_market_au",
        "artist_prior_tracks_count", "artist_avg_past_streams",
        "streams_in_the_first_month"
    ]

    df_final = df_final.select(final_columns)

    logger.info("Saving clean data to /user/team23/clean_data/ as CSV...")
    df_final.write \
        .mode("overwrite") \
        .option("header", "true") \
        .option("delimiter", ",") \
        .csv("/user/team23/clean_data/")

    logger.info("Preprocessing completed successfully")
    logger.info(
        f"Clean dataset saved with {df_final.count()} rows "
        f"and {len(final_columns)} columns"
    )

    spark.stop()


if __name__ == "__main__":
    main()
