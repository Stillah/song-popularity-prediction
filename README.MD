# Predicting Song Popularity

## Table of Contents

* [Overview](#overview)
* [Business Objective](#business-objective)
* [Dataset](#dataset)
* [Pipeline](#pipeline)
* [Feature Engineering](#feature-engineering)
* [EDA Insights](#eda-insights)
* [Machine Learning](#machine-learning)
* [Results](#results)
* [Dashboard](#dashboard)
* [Tech Stack](#tech-stack)
* [Team](#team)


# Overview

This project predicts a song’s popularity before release using Spotify chart and audio feature data.

We built a pipeline that includes:

* data preprocessing with Spark
* analytical storage with Hive
* exploratory data analysis (EDA)
* machine learning with Spark MLlib
* dashboard visualization in Apache Superset

The main prediction target is the number of streams a song receives during its first 30 days after release.


# Business Objective

## Main Goal

Predict:

```text
streams_in_the_first_month
```

— the total number of streams accumulated during the first month after release.

## Additional Goal

Identify which factors affect song popularity the most:

* artist history
* release timing
* market availability
* audio characteristics

---

# Dataset

Source: [Spotify Charts & Audio Data](https://www.kaggle.com/datasets/sunnykakar/spotify-charts-all-audio-data) (Kaggle)

Dataset characteristics:

* 26.2M rows
* 27.1 GB
* Spotify Top 200 and Viral 50 charts since 2019
* Stored in Parquet format with Snappy compression

The dataset includes:

* Spotify audio features
* stream counts
* release metadata
* artist information
* market availability

## Project Navigation

### Root Directory Structure

```
.
├── data/                  # Raw and clean data (local + HDFS paths)
├── models/                # Trained Spark ML models + detailed README
├── notebooks/             # Exploration notebooks (not used in pipeline)
├── output/                # Predictions, metrics, and exported results
├── scripts/               # All Bash and Python scripts (Stages 0–IV)
├── sql/                   # PostgreSQL schema and test queries
├── secrets/               # Credentials (not committed to Git)
├── .gitignore
├── README.md              # This file
├── main.sh                # Entry point to run the full pipeline
├── requirements.txt       # Python dependencies
└── codegen_songs.java     # Sqoop-generated schema (Parquet)
```

---

### Scripts Overview (`scripts/`)

| Script | Purpose |
|--------|---------|
| `preprocess.py` / `preprocess.sh` | Stage 0: PySpark preprocessing → clean CSV |
| `data_storage.sh` / `build_projectdb.py` | Stage I: PostgreSQL table creation + data loading |
| `sqoop_import.sh` | Stage I: Import PostgreSQL table to HDFS (Parquet + Snappy) |
| `stage1.sh` | Run all Stage I tasks |
| `stage2.sh` / `hive_setup.sh` | Stage II: Hive external tables + EDA |
| `stage3.sh` / `model.py` / `model_comparison.py` | Stage III: Spark ML regression training + evaluation |
| `stage4.sh` / `prepare_stage4.py` | Stage IV: Data preparation for Superset dashboard |
| `validate_stage2.py` / `validate_stage3.py` / `validate_stage4.py` | Validation scripts for each stage |
| `postprocess.sh` / `clean_model_output.sh` | Cleanup and output formatting |

> Each stage has a dedicated `stageX.md` file inside `scripts/` with detailed explanations.

---

### Models Directory (`models/`)

```
models/
├── model1/                # Trained Spark ML pipeline (first experiment)
├── model2/                # Trained pipeline (second experiment)
└── README.md              # Model comparison, hyperparameters, and metrics
```

---

### Quick Start

Run the entire pipeline from project root:

```bash
bash main.sh
```

To run individual stages:

```bash
bash scripts/stage1.sh   # Preprocessing + PostgreSQL + Sqoop
bash scripts/stage2.sh   # Hive EDA
bash scripts/stage3.sh   # Model training
bash scripts/stage4.sh   # Dashboard export
```


### Important Notes

- Credentials (`.psql.pass`, `kaggle.json`) must be placed in `secrets/`
- HDFS paths are hardcoded to `/user/team23/...` — adjust if needed
- All scripts must be executed from the **repository root**


# Feature Engineering

## Target Variable

The target variable:

```text
streams_in_the_first_month
```

was calculated as the total streams accumulated during the first 30 days after release.

## Main Feature Groups

### Audio Features

* danceability
* energy
* loudness
* acousticness
* instrumentalness
* valence
* tempo
* speechiness

### Release Features

* release month
* release weekday
* release week of year

### Market Features

* number of available markets
* top market indicators (US, GB, DE, JP, BR, FR, CA, AU)

### Artist Features

* previous track count
* average past streams

## Key Transformations

* cyclical encoding for musical key and dates
* log transformation for skewed features
* binary market encoding

## Removed Features

To avoid data leakage, we removed:

* chart rank
* trend
* popularity score
* region
* streams
* other post-release information


# EDA Insights

Main findings from HiveQL analysis:

* Thursday and Friday releases perform best on average
* Artist history is one of the strongest predictors of success
* Wider market availability generally improves streaming performance
* Release timing affects first-month streams
* Audio profile influences popularity, but less than artist-related features


# Machine Learning

## Models

We trained two regression models:

1. Linear Regression
2. Gradient Boosted Tree Regressor (GBTRegressor)

## Preprocessing

* `log1p` transformation of the target variable
* cyclical encoding for temporal features
* StandardScaler for Linear Regression
* 80/20 train-test split
* Grid Search + Cross Validation

## Important Features Learned by GBT

Top predictive features:

* `artist_avg_past_streams`
* `artist_prior_tracks_count`
* `af_loudness`
* `af_danceability`
* `num_markets`
* release timing features

Main conclusion:
artist history and distribution factors are more predictive than audio features alone.


# Results

| Model             | RMSE   | MAE    | R²    |
| ----------------- | ------ | ------ | ----- |
| Baseline          | 2.38   | 1.92   | 0.00  |
| Linear Regression | 2.17   | 1.75   | 0.17  |
| GBT Regressor     | **1.95** | **1.45** | **0.37** |

GBTRegressor achieved the best performance.

The relatively modest R² is expected because many important factors are unavailable before release:

* playlist placement
* TikTok virality
* social media trends
* marketing campaigns

---

# Dashboard

The Apache Superset dashboard includes:

## Dataset Overview

* dataset characteristics
* target statistics
* sample records

## EDA Section

Visualizations for:

* release timing
* market reach
* artist maturity
* artist momentum
* audio profiles

## ML Section

* model comparison
* feature importance
* prediction samples
* hyperparameter tuning results


# Tech Stack

## Data Engineering

* Hadoop HDFS
* Hive
* Parquet
* Sqoop

## Data Processing & ML

* Apache Spark
* Spark MLlib
* PySpark

## Visualization

* Apache Superset

---

# Team

| Team Member          | Role             |
| -------------------- | ---------------- |
| Dziyana Melnikava    | Data Engineer    |
| Rail Sabirov         | Data Scientist   |
| Dzhamilia Fatkullina | ML Specialist    |
| Ruslan Gatiatullin   | Technical Writer |
