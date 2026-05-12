#!/usr/bin/env python3
"""Validate Stage II local outputs."""

from pathlib import Path


REQUIRED_FILES = [
    "output/db_setup.txt",
    "output/hive_warehouse_ls.txt",
    "output/q0_characteristics.csv",
    "output/q1_release_month.csv",
    "output/q2_release_weekday.csv",
    "output/q3_market_reach.csv",
    "output/q4_artist_maturity.csv",
    "output/q5_artist_momentum.csv",
    "output/q6_audio_profile.csv",
]


def main():
    """Validate Stage II output files."""
    missing = []
    empty = []

    for file_name in REQUIRED_FILES:
        path = Path(file_name)
        if not path.exists():
            missing.append(file_name)
            continue
        if path.stat().st_size == 0:
            empty.append(file_name)

    with open("output/validate_stage2.txt", "w", encoding="utf-8") as out:
        if not missing and not empty:
            out.write("Stage II validation passed\n")
        else:
            out.write("Stage II validation failed\n")
            if missing:
                out.write("Missing files:\n")
                for item in missing:
                    out.write(f"- {item}\n")
            if empty:
                out.write("Empty files:\n")
                for item in empty:
                    out.write(f"- {item}\n")

    if missing or empty:
        raise SystemExit(1)


if __name__ == "__main__":
    main()
