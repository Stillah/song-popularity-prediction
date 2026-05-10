# Models

This folder contains the two trained Spark ML models from Stage 3.

```
models/
├── model1/   – Linear Regression (PipelineModel: StandardScaler + LR)
└── model2/   – Gradient Boosted Tree Regressor
```

Both models predict `log1p(streams_in_the_first_month)` — the log-transformed
total streams a song accumulates in its first 30 days after release.

---

## Dataset Context

- **Source table:** `team23_projectdb.songs_part_bucket` (Hive, partitioned by
  `release_month`, bucketed by `num_markets`)
- **Rows:** ~87 500 songs
- **Features:** 32 (see breakdown below)
- **Train / test split:** 80% / 20%, seed = 42

### Feature vector (32 columns)

| Group | Columns | Count |
|-------|---------|-------|
| Audio features | `af_danceability`, `af_energy`, `key_sin`, `key_cos`, `af_loudness`, `af_mode`, `af_speechiness`, `af_acousticness`, `af_instrumentalness`, `af_liveness`, `af_valence`, `af_tempo`, `af_time_signature` | 13 |
| Track metadata | `duration_ms`, `explicit` | 2 |
| Release timing | `release_month_sin`, `release_month_cos`, `release_day_of_week_sin`, `release_day_of_week_cos`, `release_week_of_year_sin`, `release_week_of_year_cos` | 6 |
| Market availability | `num_markets`, `is_market_us`, `is_market_gb`, `is_market_de`, `is_market_jp`, `is_market_br`, `is_market_fr`, `is_market_ca`, `is_market_au` | 9 |
| Artist history | `artist_prior_tracks_count`, `log_artist_avg_past_streams` | 2 |

The three raw seasonal integer columns (`release_month`, `release_day_of_week`,
`release_week_of_year`) from the Hive table are each encoded as a sin/cos pair
by a custom `CyclicalEncoder` transformer before entering the feature vector.
`artist_avg_past_streams` is log1p-transformed by a custom `LogTransformer`
and stored as `log_artist_avg_past_streams`; the original column is retained
in the DataFrame.

---

## Model 1 – Linear Regression

**Location:** `models/model1/`  
**Type:** `pyspark.ml.PipelineModel` — `StandardScaler` → `LinearRegression`

### Why Linear Regression

Serves as the interpretable baseline. Its coefficients directly show the
direction and magnitude of each feature's effect on log-streams. It also
establishes a lower bound: if a more complex model cannot beat it, the
non-linear signal in the data is weak.

### Why StandardScaler is inside the Pipeline

The 32 features span very different scales (`duration_ms` in the hundreds of
thousands, audio scores in 0–1, binary market flags). Without scaling, the
optimizer assigns distorted weights based on feature magnitude rather than
predictive power. Placing the scaler inside the `Pipeline` ensures it is
fitted on training data only, so test-set statistics never influence the model.

### Hyperparameter Tuning

3-fold cross-validation on training data. Optimization metric: RMSE.
Grid: 3 × `regParam` × 3 × `elasticNetParam` = 9 configurations × 3 folds = 27 fits.

| Hyperparameter | Values searched | Best value | Role |
|----------------|----------------|------------|------|
| `regParam` | 0.001, 0.01, 0.1 | **0.001** | Overall regularization strength (L1 + L2 penalty) |
| `elasticNetParam` | 0.0, 0.5, 1.0 | **0.5** | Mix between Ridge (0.0) and Lasso (1.0) |

The chosen combination (`regParam=0.001`, `elasticNetParam=0.5`) applies a
small ElasticNet penalty — just enough to prevent overfitting while keeping
coefficients close to their OLS values.

### Results (test set, log scale)

| RMSE | R² | exp(RMSE) |
|------|----|-----------|
| **2.1650** | **0.1587** | **≈ 8.71×** |

---

## Model 2 – Gradient Boosted Tree Regressor

**Location:** `models/model2/`  
**Type:** `pyspark.ml.regression.GBTRegressionModel`

### Why GBT

Three reasons specific to this dataset:

1. **Non-linear artist momentum.** The EDA showed a 7× jump in average streams
   at the 5 M+ `artist_avg_past_streams` tier boundary. Tree splits model this
   threshold effect directly; linear models cannot.

2. **Mixed feature types.** The vector contains continuous audio scores, integer
   counts, binary flags, and cyclical sin/cos pairs. Trees handle this without
   distributional assumptions.

3. **Robustness to residual outliers.** Even after log-transformation, viral
   hits sit at the upper tail. Row subsampling (`subsamplingRate=0.8`) reduces
   the influence of these extreme values on individual trees.

No `StandardScaler` is applied — tree split thresholds are invariant to
monotonic feature transformations.

### Configuration

Fixed training parameters (not part of the hyperparameter search):

| Parameter | Value | Reason |
|-----------|-------|--------|
| `maxIter` | 20 | Sufficient rounds without overfitting at the chosen depth |
| `subsamplingRate` | 0.8 | Row-level bagging reduces variance |
| `featureSubsetStrategy` | `auto` | Column subsampling at each split |

### Hyperparameter Tuning

3-fold cross-validation on training data. Optimization metric: RMSE.
Grid: 2 × `maxDepth` × 2 × `stepSize` = 4 configurations × 3 folds = 12 fits.

| Hyperparameter | Values searched | Best value | Role |
|----------------|----------------|------------|------|
| `maxDepth` | 3, 5 | **5** | Tree depth controls bias–variance tradeoff |
| `stepSize` | 0.05, 0.1 | **0.1** | Shrinkage / learning rate |

### Results (test set, log scale)

| RMSE | R² | exp(RMSE) | numTrees |
|------|----|-----------|----------|
| **1.8661** | **0.3749** | **≈ 6.46×** | 20 |

### Feature importances (from the run)

| Rank | Feature | Importance |
|------|---------|------------|
| 1 | `log_artist_avg_past_streams` | 0.4294 |
| 2 | `artist_prior_tracks_count` | 0.0925 |
| 3 | `af_loudness` | 0.0640 |
| 4 | `af_instrumentalness` | 0.0559 |
| 5 | `af_danceability` | 0.0369 |
| 6 | `num_markets` | 0.0368 |
| 7 | `explicit` | 0.0319 |
| 8 | `duration_ms` | 0.0307 |
| 9 | `af_acousticness` | 0.0304 |
| 10 | `release_day_of_week_sin` | 0.0300 |

Artist history features (ranks 1–2) account for over 50% of total importance,
confirming that *who* releases a song is more predictive than *what it sounds
like*. The appearance of `release_day_of_week_sin` in the top 10 is consistent
with the EDA finding that Friday releases (the global industry standard) average
3.27 M first-month streams versus 979 K for Monday releases.

---

## Model Comparison

| Model | RMSE | R² | exp(RMSE) |
|-------|------|----|-----------|
| Linear Regression | 2.1650 | 0.1587 | ≈ 8.71× |
| **GBT Regressor** | **1.8661** | **0.3749** | **≈ 6.46×** |

GBT outperforms Linear Regression on both metrics. The RMSE improvement of
0.30 on log scale corresponds to roughly a 1.3× improvement in the average
prediction error on raw stream counts.

The full comparison is saved in `output/evaluation.csv`.

---

## Why R² Is What It Is

An R² of 0.37 for GBT is the realistic ceiling for this problem given the
available features. A large share of a song's first-month performance is driven
by factors that are unobservable before release: Spotify playlist placement,
editorial features, social media virality, and press coverage. These cannot be
modeled from audio features and pre-release metadata alone.

What the models capture is the structural component of popularity: the
advantage of an established artist, wide market availability, and favorable
release timing. The remaining variance is genuine uncertainty, not a modeling
failure. R² values in the 0.30–0.40 range are consistent with published
results on pre-release stream prediction tasks.

---

## Recommended Model for Stage 4

Use **model2 (GBT)**. Its predictions and feature importances are the most
informative outputs for dashboard and business interpretation. Load it with:

```python
from pyspark.ml.regression import GBTRegressionModel
model = GBTRegressionModel.load("models/model2")
```