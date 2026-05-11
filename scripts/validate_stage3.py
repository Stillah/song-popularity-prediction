"""
validate_stage3.py – Checks that all expected Stage 3 output files
exist and are non-empty.  Writes a summary to output/validate_stage3.txt.
"""

import os
import sys

EXPECTED_FILES = [
    "data/train.json",
    "data/test.json",
    "output/model1_predictions.csv",
    "output/model2_predictions.csv",
    "output/evaluation.csv",
    "models/model1",
    "models/model2",
]


def check_files(base_dir: str) -> bool:
    """Verify each expected path exists and is non-empty."""
    all_ok = True
    results = []

    for rel_path in EXPECTED_FILES:
        full_path = os.path.join(base_dir, rel_path)
        exists = os.path.exists(full_path)

        if exists:
            # For directories (model folders), check they contain files
            if os.path.isdir(full_path):
                non_empty = bool(os.listdir(full_path))
            else:
                non_empty = os.path.getsize(full_path) > 0
        else:
            non_empty = False

        status = "OK" if (exists and non_empty) else "FAIL"
        if status == "FAIL":
            all_ok = False

        results.append((rel_path, status))
        print(f"  [{status}] {rel_path}")

    return all_ok, results


def validate_evaluation_csv(base_dir: str) -> None:
    """Print the model comparison table from evaluation.csv."""
    path = os.path.join(base_dir, "output/evaluation.csv")
    if not os.path.isfile(path):
        print("  evaluation.csv not found – skipping content check.")
        return

    with open(path, "r", encoding="utf-8") as file_head:
        content = file_head.read().strip()

    print("\n  evaluation.csv content:")
    for line in content.splitlines():
        print(f"    {line}")


def main() -> None:
    """Run all checks and report result."""
    # Assume script is called from repo root
    base_dir = os.path.abspath(os.path.join(os.path.dirname(__file__), ".."))
    print(f"Validating Stage 3 outputs under: {base_dir}\n")

    all_ok, results = check_files(base_dir)
    validate_evaluation_csv(base_dir)

    summary = "PASSED" if all_ok else "FAILED"
    print(f"\nValidation result: {summary}")

    # Write to output file (called again by stage3.sh via tee)
    out_path = os.path.join(base_dir, "output", "validate_stage3.txt")
    os.makedirs(os.path.dirname(out_path), exist_ok=True)
    with open(out_path, "w", encoding="utf-8") as file_head:
        file_head.write(f"Stage 3 validation: {summary}\n\n")
        for rel_path, status in results:
            file_head.write(f"[{status}] {rel_path}\n")

    if not all_ok:
        sys.exit(1)


if __name__ == "__main__":
    main()
