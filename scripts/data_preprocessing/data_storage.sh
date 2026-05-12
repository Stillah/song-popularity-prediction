#!/bin/bash

set -e

echo "Data Storage"

source venv/bin/activate

python scripts/data_preprocessing/build_projectdb.py

echo "Table created and data loaded successfully!"