"""Validate Stage 4 dashboard preparation outputs."""

import csv
import os
import sys
from typing import List, Tuple


OUTPUT_DIR = os.path.join("output", "stage4")
VALIDATION_PATH = os.path.join("output", "validate_stage4.txt")

EXPECTED_CSV_FILES = {
    "ml_model_comparison.csv": [
        "model",
        "rmse",
        "mae",
        "r2",
        "exp_mae",
        "interpretation",
    ],

    "ml_prediction_samples.csv": [
        "sample_id",
        "model",
        "actual_log_streams",
        "predicted_log_streams",
        "actual_streams_est",
        "predicted_streams_est",
        "abs_error_log",
    ],
    "ml_hyperparameters.csv": [
        "model",
        "hyperparameter",
        "searched_values",
        "best_value",
        "reason",
    ],
    "ml_feature_summary.csv": [
        "feature_group",
        "feature_name",
        "description",
        "used_in_model",
    ],
    "ml_feature_importance.csv": ["rank", "feature", "importance", "model"],
}

EXPECTED_TEXT_FILES = ["dashboard_story.md"]


def validate_csv(path: str, expected_columns: List[str]) -> Tuple[bool, str]:
    """Check file existence, header, and at least one data row."""
    if not os.path.isfile(path):
        return False, "missing"
    if os.path.getsize(path) == 0:
        return False, "empty"

    with open(path, "r", encoding="utf-8", newline="") as file_handle:
        reader = csv.DictReader(file_handle)
        if reader.fieldnames != expected_columns:
            return False, "invalid header"
        row_count = sum(1 for _ in reader)

    if row_count == 0:
        return False, "no rows"
    return True, f"{row_count} rows"


def validate_text_file(path: str) -> Tuple[bool, str]:
    """Check a non-CSV output file."""
    if not os.path.isfile(path):
        return False, "missing"
    if os.path.getsize(path) == 0:
        return False, "empty"
    return True, "ok"


def main() -> None:
    """Run all Stage 4 validation checks."""
    results = []
    all_ok = True

    for filename, expected_columns in EXPECTED_CSV_FILES.items():
        path = os.path.join(OUTPUT_DIR, filename)
        is_ok, message = validate_csv(path, expected_columns)
        status = "OK" if is_ok else "FAIL"
        all_ok = all_ok and is_ok
        results.append((filename, status, message))
        print(f"[{status}] {filename}: {message}")

    for filename in EXPECTED_TEXT_FILES:
        path = os.path.join(OUTPUT_DIR, filename)
        is_ok, message = validate_text_file(path)
        status = "OK" if is_ok else "FAIL"
        all_ok = all_ok and is_ok
        results.append((filename, status, message))
        print(f"[{status}] {filename}: {message}")

    summary = "PASSED" if all_ok else "FAILED"
    os.makedirs(os.path.dirname(VALIDATION_PATH), exist_ok=True)
    with open(VALIDATION_PATH, "w", encoding="utf-8") as file_handle:
        file_handle.write(f"Stage 4 validation: {summary}\n\n")
        for filename, status, message in results:
            file_handle.write(f"[{status}] {filename}: {message}\n")

    print(f"\nStage 4 validation: {summary}")
    if not all_ok:
        sys.exit(1)


if __name__ == "__main__":
    main()
