#!/bin/bash
set -euo pipefail

mkdir -p output

{
    echo "=== pylint: scripts/prepare_stage4.py ==="
    pylint scripts/prepare_stage4.py
    echo ""
    echo "=== pylint: scripts/validate_stage4.py ==="
    pylint scripts/validate_stage4.py
} > output/pylint_stage4.txt
