#!/bin/bash
set -euo pipefail

mkdir -p output

{
    echo "=== pylint: scripts/visualizations/prepare_stage4.py ==="
    pylint scripts/visualizations/prepare_stage4.py
    echo ""
    echo "=== pylint: scripts/code_style/validate_stage4.py ==="
    pylint scripts/code_style/validate_stage4.py
} > output/pylint_stage4.txt
