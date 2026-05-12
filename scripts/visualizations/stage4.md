# Stage IV - Presentation and Delivery

## Overview

In this stage I prepared the final presentation layer for the project. The
main goal was to create the data sources needed for an Apache Superset
dashboard that presents the dataset characteristics, EDA findings, and Spark ML
modeling results.

The dashboard itself is created manually in Apache Superset, because chart
creation and dashboard publishing are not automated in the project checklist.
The repository scripts automate the preparation of all small result tables used
by Superset.

## Added files in Stage IV

### `scripts/visualizations/prepare_stage4.py`

This script reads the existing Stage II and Stage III output files and prepares
clean dashboard datasets in `output/stage4/`.

It creates:

| File | Purpose |
|------|---------|
| `output/stage4/ml_model_comparison.csv` | RMSE, R2, and log-scale interpretation for both models |
| `output/stage4/ml_prediction_samples.csv` | A small actual-vs-predicted sample for both models |
| `output/stage4/ml_hyperparameters.csv` | Searched and selected hyperparameters |
| `output/stage4/ml_feature_summary.csv` | Feature groups and feature descriptions |
| `output/stage4/ml_feature_importance.csv` | Top GBT feature importances for dashboard charts |
| `output/stage4/dashboard_story.md` | Short text for Superset text panels |

### `sql/stage4_ml_tables.hql`

This HiveQL file creates external Hive tables over the Stage IV CSV files in
HDFS. These tables are used as Superset datasets for the ML section of the
dashboard.

Created tables:

| Hive table | Dashboard use |
|------------|---------------|
| `stage4_ml_model_comparison` | Model comparison chart |
| `stage4_ml_prediction_samples` | Prediction sample chart |
| `stage4_ml_hyperparameters` | Hyperparameter tuning table |
| `stage4_ml_feature_summary` | Feature extraction table |
| `stage4_ml_feature_importance` | GBT feature importance chart |

### `scripts/code_style/validate_stage4.py`

This script checks that all expected Stage IV output files exist, are not
empty, and have the expected columns.

### `scripts/code_style/pylint_stage4.sh`

This script runs pylint for the Stage IV Python scripts and saves the result to
`output/pylint_stage4.txt`.

### `scripts/visualizations/stage4.sh`

This is the main entry point for Stage IV. It prepares dashboard files, uploads
them to HDFS, creates Hive external tables, validates the output, and runs
pylint.

## Dashboard structure

I organized the Superset dashboard into four sections.

### 1. Dataset Overview

This section explains the prepared dataset before modeling.

Recommended Superset datasets:

| Dataset | Source |
|---------|--------|
| `q0_results` | Stage II dataset characteristics |
| `songs_part_bucket` | Main Hive analytical table |

Recommended charts and panels:

| Panel | Description |
|-------|-------------|
| Dataset characteristics | Row count, feature count, target column, partition column, bucket column |
| Target summary | Minimum, average, and maximum first-month streams |
| Data sample | Several rows from `songs_part_bucket` |
| Cleaning note | Short text about preprocessing and feature extraction |

### 2. EDA Insights

This section presents the six Stage II insights. Each chart should include a
short conclusion text panel.

| Insight | Hive table | Suggested chart |
|---------|------------|-----------------|
| Release month | `q1_results` | Bar chart: average streams by month |
| Release weekday | `q2_results` | Bar chart: average streams by weekday |
| Market reach | `q3_results` | Bar chart: streams by market reach tier |
| Artist maturity | `q4_results` | Bar chart: streams by maturity tier |
| Artist momentum | `q5_results` | Bar chart: streams by artist history tier |
| Audio profile | `q6_results` | Bar chart: streams by audio profile |

Main story:

- Release timing is connected with launch performance.
- Market reach is important, but the relationship is not perfectly linear.
- Artist history is one of the strongest business signals.
- Audio profile gives useful context, but it is weaker than artist and release
  factors.

### 3. ML Modeling Results

This section presents the results from Spark ML.

Recommended Superset datasets:

| Dataset | Suggested chart |
|---------|-----------------|
| `stage4_ml_feature_summary` | Table grouped by feature category |
| `stage4_ml_hyperparameters` | Table of searched and best hyperparameters |
| `stage4_ml_model_comparison` | Bar chart for RMSE and R2 |
| `stage4_ml_prediction_samples` | Line or scatter chart: actual vs predicted |
| `stage4_ml_feature_importance` | Horizontal bar chart of top GBT features |

Main story:

- Both models predict `log1p(streams_in_the_first_month)`.
- Linear Regression is the interpretable baseline.
- GBT Regressor performs better because it captures non-linear effects.
- The most important feature is artist past performance.

### 4. Business Story and Recommendations

This section summarizes the dashboard as a business narrative.

Recommended text:

> The best pre-release signals for first-month streaming performance are artist
> history, release timing, and market availability. Audio features help explain
> the song profile, but they are less predictive than artist and distribution
> variables. The GBT model should be used as the main predictive model because
> it achieved lower RMSE and higher R2 on the test set.

Recommended decisions:

- Give more attention to artists with strong recent streaming history.
- Prefer release timing that historically correlates with stronger launches.
- Use wide market availability as part of release planning.
- Treat model predictions as decision support, because playlist placement and
  viral effects are not present in the available features.

## Running Stage IV on the cluster

Run the script from the repository root:

```bash
bash scripts/stage4.sh
```

Expected outputs:

```text
output/stage4/ml_model_comparison.csv
output/stage4/ml_prediction_samples.csv
output/stage4/ml_hyperparameters.csv
output/stage4/ml_feature_summary.csv
output/stage4/ml_feature_importance.csv
output/stage4/dashboard_story.md
output/stage4_hive_tables.txt
output/validate_stage4.txt
output/pylint_stage4.txt
```

## Manual Superset steps

After running `scripts/stage4.sh`, I created Superset datasets for the Stage IV
Hive tables:

```text
stage4_ml_model_comparison
stage4_ml_prediction_samples
stage4_ml_hyperparameters
stage4_ml_feature_summary
stage4_ml_feature_importance
```

Then I added the existing Stage II result tables:

```text
q0_results
q1_results
q2_results
q3_results
q4_results
q5_results
q6_results
```

Finally, I created and published one Superset dashboard containing the dataset
overview, EDA insights, ML modeling results, and final recommendations.

## Stage IV checklist

| Requirement | Status |
|-------------|--------|
| Dashboard title added | Done manually in Superset |
| Dashboard layout organized | Done manually in Superset |
| Dataset characteristics shown | Based on `q0_results` and `songs_part_bucket` |
| Stage II charts included | Based on `q1_results` to `q6_results` |
| ML result tables prepared | Automated in `scripts/prepare_stage4.py` |
| External Hive tables for ML results created | Automated in `sql/stage4_ml_tables.hql` |
| Model performance shown | Based on `stage4_ml_model_comparison` |
| Prediction results shown | Based on `stage4_ml_prediction_samples` |
| Dashboard story included | Based on text panels and `dashboard_story.md` |
| Script validation added | `scripts/validate_stage4.py` |
| Pylint check added | `scripts/pylint_stage4.sh` |
