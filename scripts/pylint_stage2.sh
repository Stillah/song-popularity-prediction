#!/bin/bash
set -e

mkdir -p output
source venv/bin/activate

pylint scripts/validate_stage2.py > output/pylint_stage2.txt || true

echo "Pylint finished. See output/pylint_stage2.txt"