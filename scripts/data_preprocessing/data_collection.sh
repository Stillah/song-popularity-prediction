#!/bin/bash
set -e

echo "Data collection"

mkdir -p data

if ! command -v kaggle &> /dev/null; then
    echo "Installing Kaggle CLI..."
    pip install kaggle --quiet
fi

mkdir -p ~/.kaggle
cp secrets/kaggle.json ~/.kaggle/
chmod 600 ~/.kaggle/kaggle.json

if [ -f "data/merged_data.csv" ]; then
    echo "Dataset already exists at data/merged_data.csv"
    exit 0
fi

echo "Downloading dataset..."
kaggle datasets download sunnykakar/spotify-charts-all-audio-data -p data/

echo "Unzipping..."
unzip -o data/spotify-charts-all-audio-data.zip -d data/

rm data/spotify-charts-all-audio-data.zip

echo "Data collection complete"