#!/bin/bash
set -euo pipefail

REPO_ROOT="$(cd "$(dirname "$0")/.." && pwd)"
OUTPUT_FILE="$REPO_ROOT/output/pylint_stage3.txt"

echo "Running pylint on Stage 3 scripts..."

{
    echo "=== pylint: scripts/model.py ==="
    pylint "$REPO_ROOT/scripts/model.py" \
        --disable=C0114,C0115,C0116,W0511,R0914,R0915 \
        --max-line-length=100 \
        || true

    echo ""
    echo "=== pylint: scripts/validate_stage3.py ==="
    pylint "$REPO_ROOT/scripts/validate_stage3.py" \
        --disable=C0114,C0115,C0116,W0511 \
        --max-line-length=100 \
        || true
} 2>&1 | tee "$OUTPUT_FILE"

echo "Pylint results saved to output/pylint_stage3.txt"