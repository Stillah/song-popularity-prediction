#!/usr/bin/env python3
"""Model comparison script"""
import os
import math
import pandas as pd


def run(command: str) -> str:
    """Run terminal command"""
    return os.popen(command).read()


# Load predictions from CSV files
print("Loading predictions...")

try:
    # Load LR predictions
    lr_pred = pd.read_csv("output/model1_predictions.csv")
    # Load GBT predictions
    gbt_pred = pd.read_csv("output/model2_predictions.csv")
except FileNotFoundError:
    # Try HDFS
    run("hdfs dfs -cat project/output/model1_predictions/*.csv >\
    /tmp/lr_pred.csv")
    run("hdfs dfs -cat project/output/model2_predictions/*.csv >\
    /tmp/gbt_pred.csv")
    lr_pred = pd.read_csv("/tmp/lr_pred.csv")
    gbt_pred = pd.read_csv("/tmp/gbt_pred.csv")


# Calculate metrics manually
def rmse(y_true, y_pred):
    """Calculate RMSE"""
    return math.sqrt(((y_true - y_pred) ** 2).mean())


def r_2(y_true, y_pred):
    """Calculate R^2"""
    ss_res = ((y_true - y_pred) ** 2).sum()
    ss_tot = ((y_true - y_true.mean()) ** 2).sum()
    return 1 - (ss_res / ss_tot)


def mae(y_true, y_pred):
    """Calculate MAE"""
    res = (y_true - y_pred).abs().mean()
    return res


def baseline(y_true):
    """Calculate baseline predictions."""
    return (y_true - y_true.mean()).mean()


# LR metrics
rmse_lr = rmse(lr_pred['label'], lr_pred['prediction'])
r2_lr = r_2(lr_pred['label'], lr_pred['prediction'])
mae_lr = mae(lr_pred['label'], lr_pred['prediction'])

# GBT metrics
rmse_gbt = rmse(gbt_pred['label'], gbt_pred['prediction'])
r2_gbt = r_2(gbt_pred['label'], gbt_pred['prediction'])
mae_gbt = mae(gbt_pred['label'], gbt_pred['prediction'])

# Baseline
global_mean = lr_pred['label'].mean()
rmse_baseline = rmse(lr_pred['label'], global_mean)
r2_baseline = r_2(lr_pred['label'], global_mean)
mae_baseline = mae(lr_pred['label'], global_mean)

print("\n=== Model Comparison ===")
print(f"{'Model':<20} {'RMSE':<10} {'MAE':<10} {'R2':<10}")
print("-" * 50)
print(f"{'LinearRegression':<20} {rmse_lr:<10.4f} {mae_lr:<10.4f} \
        {r2_lr:<10.4f}")
print(f"{'GBTRegressor':<20} {rmse_gbt:<10.4f} {mae_gbt:<10.4f} \
        {r2_gbt:<10.4f}")
print(f"{'Baseline (global mean)':<10} {rmse_baseline:<10.4f} \
        {mae_baseline:<10.4f} {r2_baseline:<10.4f}")


# Save comparison
comparison = pd.DataFrame([
    ["LinearRegression", round(rmse_lr, 4), round(mae_lr, 4), round(r2_lr, 4)],
    ["GBTRegressor", round(rmse_gbt, 4), round(mae_gbt, 4), round(r2_gbt, 4)]
], columns=["model", "RMSE", "MAE", "R2"])

comparison.to_csv("output/evaluation.csv", index=False)
print("\nSaved to output/evaluation.csv")

# Sanity check
print("\n=== Sanity Check ===")
print(f"LR  exp(MAE) ≈ {math.exp(mae_lr):.2f}×")
print(f"GBT exp(MAE) ≈ {math.exp(mae_gbt):.2f}×")

# Best model
print("\n=== Best Model ===")
if rmse_lr < rmse_gbt:
    print(f"Linear Regression (RMSE: {rmse_lr:.4f} < {rmse_gbt:.4f})")
else:
    print(f"GBT Regressor (RMSE: {rmse_gbt:.4f} < {rmse_lr:.4f})")
