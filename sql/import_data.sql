COPY songs (
    af_danceability, af_energy, key_sin, key_cos, af_loudness, af_mode,
    af_speechiness, af_acousticness, af_instrumentalness, af_liveness,
    af_valence, af_tempo, af_time_signature, duration_ms, explicit,
    release_month, release_day_of_week, release_week_of_year, num_markets,
    is_market_us, is_market_gb, is_market_de, is_market_jp, is_market_br,
    is_market_fr, is_market_ca, is_market_au, artist_prior_tracks_count,
    artist_avg_past_streams, streams_in_the_first_month
) FROM STDIN WITH CSV HEADER DELIMITER ',' NULL AS 'null';