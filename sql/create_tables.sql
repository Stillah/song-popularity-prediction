START TRANSACTION;

DROP TABLE IF EXISTS songs CASCADE;

CREATE TABLE IF NOT EXISTS songs (
    id SERIAL PRIMARY KEY,
    af_danceability DOUBLE PRECISION,
    af_energy DOUBLE PRECISION,
    key_sin DOUBLE PRECISION,
    key_cos DOUBLE PRECISION,
    af_loudness DOUBLE PRECISION,
    af_mode INTEGER,
    af_speechiness DOUBLE PRECISION,
    af_acousticness DOUBLE PRECISION,
    af_instrumentalness DOUBLE PRECISION,
    af_liveness DOUBLE PRECISION,
    af_valence DOUBLE PRECISION,
    af_tempo DOUBLE PRECISION,
    af_time_signature DOUBLE PRECISION,
    duration_ms DOUBLE PRECISION,
    explicit INTEGER,
    release_month INTEGER,
    release_day_of_week INTEGER,
    release_week_of_year INTEGER,
    num_markets INTEGER,
    is_market_us INTEGER,
    is_market_gb INTEGER,
    is_market_de INTEGER,
    is_market_jp INTEGER,
    is_market_br INTEGER,
    is_market_fr INTEGER,
    is_market_ca INTEGER,
    is_market_au INTEGER,
    artist_prior_tracks_count INTEGER,
    artist_avg_past_streams DOUBLE PRECISION,
    streams_in_the_first_month DOUBLE PRECISION
);

COMMIT;