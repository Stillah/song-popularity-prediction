#!/bin/bash
set -euo pipefail

echo "  Stage 3: Spark ML – Song Popularity Prediction"

# Step 1 – Run the main Spark ML job
echo "[1/4] Running Spark ML job (model.py) via spark-submit..."
spark-submit \
    --master yarn \
    --num-executors 4 \
    --executor-cores 2 \
    --executor-memory 4g \
    --driver-memory 2g \
    "scripts/model.py" \
    2>&1 | tee "output/model_output.txt"

echo "      spark-submit finished."

# Step 2 – Validate outputs
echo "[2/4] Validating Stage 3 outputs..."
python3 "scripts/validate_stage3.py" \
    2>&1 | tee "output/validate_stage3.txt"

# Step 3 – Pylint check
echo "[3/4] Running pylint on model.py and validate_stage3.py..."
bash "scripts/pylint_stage3.sh"

# Step 4 – Summary
echo "[4/4] Stage 3 complete. Key output files:"
echo "      data/train.json"
echo "      data/test.json"
echo "      output/model1_predictions.csv"
echo "      output/model2_predictions.csv"
echo "      output/evaluation.csv"
echo "      output/validate_stage3.txt"
echo "      output/pylint_stage3.txt"
echo "      models/model1/"
echo "      models/model2/"
echo ""
echo "Evaluation summary:"
cat "output/evaluation.csv"
