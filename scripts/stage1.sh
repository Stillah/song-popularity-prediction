#!/bin/bash

set -e

echo "Stage I: Data Storage & Import"

bash scripts/data_storage.sh

bash scripts/sqoop_import.sh

echo "Stage I completed"