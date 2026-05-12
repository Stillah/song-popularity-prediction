#!/bin/bash

set -e

echo "Stage I: Data Storage & Import"

bash scripts/data_preprocessing/data_storage.sh

bash scripts/data_preprocessing/sqoop_import.sh

echo "Stage I completed"