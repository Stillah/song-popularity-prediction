"""Prepare files for stage 4 dashboards"""
import csv
import math
import os
from typing import Dict, Iterable, List


OUTPUT_DIR = os.path.join("output", "stage4")
EVALUATION_PATH = os.path.join("output", "evaluation.csv")
MODEL1_PREDICTIONS_PATH = os.path.join("output", "model1_predictions.csv")
MODEL2_PREDICTIONS_PATH = os.path.join("output", "model2_predictions.csv")
PREDICTION_SAMPLE_SIZE = 25


FEATURES = [
    ("Audio features", "af_danceability", "Danceability score from Spotify audio analysis"),
    ("Audio features", "af_energy", "Energy score from Spotify audio analysis"),
    ("Audio features", "key_sin", "Sine encoding of musical key"),
    ("Audio features", "key_cos", "Cosine encoding of musical key"),
    ("Audio features", "af_loudness", "Track loudness in decibels"),
    ("Audio features", "af_mode", "Musical mode flag"),
    ("Audio features", "af_speechiness", "Speechiness score"),
    ("Audio features", "af_acousticness", "Acousticness score"),
    ("Audio features", "af_instrumentalness", "Instrumentalness score"),
    ("Audio features", "af_liveness", "Liveness score"),
    ("Audio features", "af_valence", "Musical positivity score"),
    ("Audio features", "af_tempo", "Tempo in beats per minute"),
    ("Audio features", "af_time_signature", "Time signature"),
    ("Track metadata", "duration_ms", "Track duration in milliseconds"),
    ("Track metadata", "explicit", "Explicit content flag"),
    ("Release timing", "release_month", "Month of release"),
    ("Release timing", "release_day_of_week", "Day of week of release"),
    ("Release timing", "release_week_of_year", "Week number of release"),
    ("Market availability", "num_markets", "Number of available Spotify markets"),
    ("Market availability", "is_market_us", "Availability in the United States"),
    ("Market availability", "is_market_gb", "Availability in Great Britain"),
    ("Market availability", "is_market_de", "Availability in Germany"),
    ("Market availability", "is_market_jp", "Availability in Japan"),
    ("Market availability", "is_market_br", "Availability in Brazil"),
    ("Market availability", "is_market_fr", "Availability in France"),
    ("Market availability", "is_market_ca", "Availability in Canada"),
    ("Market availability", "is_market_au", "Availability in Australia"),
    ("Artist history", "artist_prior_tracks_count", "Previous tracks by the artist"),
    ("Artist history", "log_artist_avg_past_streams", "Log-transformed artist past streams"),
]


HYPERPARAMETERS = [
    (
        "Linear Regression",
        "regParam",
        "0.001; 0.01; 0.1",
        "0.001",
        "Controls regularization strength",
    ),
    (
        "Linear Regression",
        "elasticNetParam",
        "0.0; 0.5; 1.0",
        "0.0",
        "Controls the Ridge and Lasso regularization mix",
    ),
    (
        "GBT Regressor",
        "maxDepth",
        "3; 5",
        "5",
        "Controls the depth and complexity of each tree",
    ),
    (
        "GBT Regressor",
        "stepSize",
        "0.05; 0.1",
        "0.1",
        "Controls the boosting learning rate",
    ),
]


FEATURE_IMPORTANCES = [
    ("log_artist_avg_past_streams", 0.4091),
    ("artist_prior_tracks_count", 0.1014),
    ("af_loudness", 0.0634),
    ("af_instrumentalness", 0.0421),
    ("af_acousticness", 0.0410),
    ("explicit", 0.0377),
    ("duration_ms", 0.0353),
    ("af_danceability", 0.0331),
    ("num_markets", 0.0308),
    ("release_day_of_week_sin", 0.0269),
]


IMPORTANCE = 0
for _, fraction in FEATURE_IMPORTANCES:
    IMPORTANCE += fraction

FEATURE_IMPORTANCES.append(('other', 1-IMPORTANCE))


def ensure_output_dir() -> None:
    """Create the Stage 4 output directory."""
    os.makedirs(OUTPUT_DIR, exist_ok=True)


def short_model_name(raw_name: str) -> str:
    """Convert Spark model descriptions to dashboard-friendly names."""
    if "GBTRegressionModel" in raw_name:
        return "GBT Regressor"
    if "LinearRegressionModel" in raw_name:
        return "Linear Regression"
    return raw_name


def write_csv(path: str, fieldnames: List[str],
              rows: Iterable[Dict[str, object]]) -> None:
    """Write rows to a CSV file with a stable header."""
    with open(path, "w", encoding="utf-8", newline="") as file_handle:
        writer = csv.DictWriter(file_handle, fieldnames=fieldnames)
        writer.writeheader()
        writer.writerows(rows)


def prepare_model_comparison() -> None:
    """Prepare model comparison table for Superset."""
    rows = []
    with open(EVALUATION_PATH, "r", encoding="utf-8", newline="") as file_handle:
        reader = csv.DictReader(file_handle)
        for row in reader:
            rmse = float(row["RMSE"])
            mae = float(row["MAE"])
            r2_score = float(row["R2"])
            model_name = short_model_name(row["model"])

            if model_name == "GBT Regressor":
                interpretation = "Best model; lower RMSE, lower MAE, and higher R2 on test data"
            else:
                interpretation = "Interpretable baseline model"

            rows.append(
                {
                    "model": model_name,
                    "rmse": f"{rmse:.4f}",
                    "mae": f"{mae:.4f}",
                    "r2": f"{r2_score:.4f}",
                    "exp_mae": f"{math.exp(mae):.2f}",
                    "interpretation": interpretation,
                }
            )

    write_csv(
        os.path.join(OUTPUT_DIR, "ml_model_comparison.csv"),
        ["model", "rmse", "mae", "r2", "exp_mae", "interpretation"],
        rows,
    )



def prediction_rows(path: str, model_name: str) -> List[Dict[str, object]]:
    """Read a small prediction sample from one model output file."""
    rows = []
    with open(path, "r", encoding="utf-8", newline="") as file_handle:
        reader = csv.DictReader(file_handle)
        for sample_id, row in enumerate(reader, start=1):
            if sample_id > PREDICTION_SAMPLE_SIZE:
                break

            actual_log = float(row["label"])
            predicted_log = float(row["prediction"])
            rows.append(
                {
                    "sample_id": sample_id,
                    "model": model_name,
                    "actual_log_streams": f"{actual_log:.4f}",
                    "predicted_log_streams": f"{predicted_log:.4f}",
                    "actual_streams_est": int(round(math.expm1(actual_log))),
                    "predicted_streams_est": int(round(math.expm1(predicted_log))),
                    "abs_error_log": f"{abs(actual_log - predicted_log):.4f}",
                }
            )
    return rows


def prepare_prediction_samples() -> None:
    """Prepare model prediction samples for dashboard charts."""
    rows = []
    rows.extend(prediction_rows(MODEL1_PREDICTIONS_PATH, "Linear Regression"))
    rows.extend(prediction_rows(MODEL2_PREDICTIONS_PATH, "GBT Regressor"))

    write_csv(
        os.path.join(OUTPUT_DIR, "ml_prediction_samples.csv"),
        [
            "sample_id",
            "model",
            "actual_log_streams",
            "predicted_log_streams",
            "actual_streams_est",
            "predicted_streams_est",
            "abs_error_log",
        ],
        rows,
    )


def prepare_hyperparameters() -> None:
    """Prepare selected hyperparameters and searched values."""
    rows = [
        {
            "model": model,
            "hyperparameter": hyperparameter,
            "searched_values": searched_values,
            "best_value": best_value,
            "reason": reason,
        }
        for model, hyperparameter, searched_values, best_value, reason in HYPERPARAMETERS
    ]
    write_csv(
        os.path.join(OUTPUT_DIR, "ml_hyperparameters.csv"),
        ["model", "hyperparameter", "searched_values", "best_value", "reason"],
        rows,
    )


def prepare_feature_summary() -> None:
    """Prepare the feature extraction summary."""
    rows = [
        {
            "feature_group": feature_group,
            "feature_name": feature_name,
            "description": description,
            "used_in_model": "yes",
        }
        for feature_group, feature_name, description in FEATURES
    ]
    write_csv(
        os.path.join(OUTPUT_DIR, "ml_feature_summary.csv"),
        ["feature_group", "feature_name", "description", "used_in_model"],
        rows,
    )


def prepare_feature_importance() -> None:
    """Prepare GBT feature importances for dashboard charts."""
    rows = [
        {
            "rank": rank,
            "feature": feature,
            "importance": f"{importance:.4f}",
            "model": "GBT Regressor",
        }
        for rank, (feature, importance) in enumerate(FEATURE_IMPORTANCES, start=1)
    ]
    write_csv(
        os.path.join(OUTPUT_DIR, "ml_feature_importance.csv"),
        ["rank", "feature", "importance", "model"],
        rows,
    )


def prepare_dashboard_story() -> None:
    """Write a short dashboard story file for Superset text panels."""
    story_path = os.path.join(OUTPUT_DIR, "dashboard_story.md")
    content = """# Song Popularity Prediction Dashboard

The dashboard presents the project pipeline from data preparation to business
recommendations. The target variable is first-month Spotify streams.

## Main Findings

- Artist history is the strongest signal for future stream performance.
- Friday releases and wider market availability are connected with stronger launches.
- Audio features add useful context, but they are weaker predictors than artist and release factors.
- GBT Regressor is the best model because it captures non-linear effects better than Linear Regression.

## Business Recommendation

Release planning should prioritize artist momentum, launch timing, and market
coverage. The model can support early expectations for new tracks, but viral
effects and playlist placement remain outside the available feature set.
"""
    with open(story_path, "w", encoding="utf-8") as file_handle:
        file_handle.write(content)


def main() -> None:
    """Build all Stage 4 files."""
    ensure_output_dir()
    prepare_model_comparison()
    prepare_prediction_samples()
    prepare_hyperparameters()
    prepare_feature_summary()
    prepare_feature_importance()
    prepare_dashboard_story()
    print(f"Stage 4 dashboard files were written to {OUTPUT_DIR}")


if __name__ == "__main__":
    main()
