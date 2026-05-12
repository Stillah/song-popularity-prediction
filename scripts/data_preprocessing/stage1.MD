
# Data Preprocessing for Song Popularity Prediction

## Overview

This preprocessing pipeline transforms the raw Spotify Charts dataset into a clean, structured dataset ready for PostgreSQL storage and subsequent machine learning. The output dataset contains **30 columns**: 29 features and 1 target variable.

**Scripts:**
- `scripts/data_preprocessing/preprocess.sh` – Launches the PySpark job
- `scripts/data_preprocessing/preprocess.py` – Performs all transformations using Spark

---

## Key Decisions & Justification

### 1. Target Variable: `streams_in_the_first_month`

**Why:** The business goal is to predict a song's popularity **before release**. The natural proxy for post-release popularity is the number of streams accumulated during the first 30 days.

**Calculation:**
- Filter rows where `chart = 'top200'` (viral50 has NULL streams)
- Compute `days_since_release = date - release_date`
- Keep only days 0–29 after release
- Group by `track_id` and sum `streams`

**Why not use `popularity` column?** The `popularity` field in the dataset reflects post-release metrics and would cause data leakage.

---

### 2. Feature Set (29 features)

| Category | Features | Count | Justification |
|----------|----------|-------|---------------|
| **Audio features** | `af_danceability`, `af_energy`, `key_sin`, `key_cos`, `af_loudness`, `af_mode`, `af_speechiness`, `af_acousticness`, `af_instrumentalness`, `af_liveness`, `af_valence`, `af_tempo`, `af_time_signature` | 13 | Available before release, Spotify’s audio analysis has proven predictive power for popularity |
| **Track metadata** | `duration_ms`, `explicit` | 2 | Known before release |
| **Seasonal features** | `release_month`, `release_day_of_week`, `release_week_of_year` | 3 | Extracted from `release_date`, capture release timing effects (e.g., Friday releases, holiday seasons) |
| **Market availability** | `num_markets` + 8 binary flags (`US`, `GB`, `DE`, `JP`, `BR`, `FR`, `CA`, `AU`) | 9 | Derived from `available_markets` |
| **Artist history** | `artist_prior_tracks_count`, `artist_avg_past_streams` | 2 | Computed from previous tracks of the same artist, established artists tend to perform better |

---

### 3. Removed Columns (Not Used in Model)

| Column | Reason for Removal |
|--------|---------------------|
| `region` | Post-release information |
| `rank`, `trend`, `date` | Post-release chart positions |
| `chart` | Post-release (top200/viral50) |
| `streams` | Used only to build target variable |
| `popularity` | Post-release metric |
| `url`, `track_id`, `album` | Non-informative or identifiers |
| `available_markets` (raw) | Replaced by derived features |

---

### 4. Cyclical Encoding of `af_key`

**Why:** Musical key is cyclical (C → C# → … → B → C). Treating it as a linear integer (0–11) would mislead the model (e.g., 11 and 0 are close in pitch but far numerically).

**Solution:** Convert to two features using sine/cosine transformation:

```
key_sin = sin(2π * key / 12)
key_cos = cos(2π * key / 12)
```

---

### 5. Artist History

**Challenge:** For each song, we need the artist’s past performance **before** this song’s release date.

**Solution:** Use Spark Window functions:
- Partition by `artist`
- Order by `release_date`
- For each row, consider only rows with `release_date` < current row’s `release_date`

**Features:**
- `artist_prior_tracks_count` – Number of previous tracks by the same artist
- `artist_avg_past_streams` – Average `streams_in_the_first_month` of previous tracks

First-time artists receive 0 for both features.

---

### 6. Handling `available_markets`
Top 8 markets (US, GB, DE, JP, BR, FR, CA, AU) represent the largest music markets by streaming volume and cultural influence.

---

## Final Dataset Schema

| Column | Type | Description |
|--------|------|-------------|
| `af_danceability` | double | Danceability score (0–1) |
| `af_energy` | double | Energy score (0–1) |
| `key_sin` | double | Cyclical encoding of key (sine) |
| `key_cos` | double | Cyclical encoding of key (cosine) |
| `af_loudness` | double | Loudness (dB) |
| `af_mode` | int | Mode (0 = minor, 1 = major) |
| `af_speechiness` | double | Speechiness score (0–1) |
| `af_acousticness` | double | Acousticness score (0–1) |
| `af_instrumentalness` | double | Instrumentalness score (0–1) |
| `af_liveness` | double | Liveness score (0–1) |
| `af_valence` | double | Valence (musical positivity, 0–1) |
| `af_tempo` | double | Tempo (BPM) |
| `af_time_signature` | double | Time signature (e.g., 4 for 4/4) |
| `duration_ms` | double | Track duration in milliseconds |
| `explicit` | int | 1 = explicit, 0 = not explicit |
| `release_month` | int | Month of release (1–12) |
| `release_day_of_week` | int | Day of week (1 = Monday, 7 = Sunday) |
| `release_week_of_year` | int | Week number (1–53) |
| `num_markets` | int | Number of countries where track is available |
| `is_market_us` | int | 1 = available in US |
| `is_market_gb` | int | 1 = available in GB |
| `is_market_de` | int | 1 = available in DE |
| `is_market_jp` | int | 1 = available in JP |
| `is_market_br` | int | 1 = available in BR |
| `is_market_fr` | int | 1 = available in FR |
| `is_market_ca` | int | 1 = available in CA |
| `is_market_au` | int | 1 = available in AU |
| `artist_prior_tracks_count` | int | Number of previous tracks by the same artist |
| `artist_avg_past_streams` | double | Average first-month streams of previous tracks |
| **`streams_in_the_first_month`** (target) | double | Total streams in first 30 days |

---
