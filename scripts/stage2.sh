#!/bin/bash
set -e

echo "Stage II: Data Storage/Preparation & EDA"

mkdir -p output

echo "Running Hive setup..."
bash scripts/hive_setup.sh

echo "Running q0..."
bash scripts/run_hive_query.sh q0_characteristics "metric,value"

echo "Running q1..."
bash scripts/run_hive_query.sh q1_release_month "release_month,track_count,avg_first_month_streams"

echo "Running q2..."
bash scripts/run_hive_query.sh q2_release_weekday "weekday_num,release_weekday,track_count,avg_first_month_streams"

echo "Running q3..."
bash scripts/run_hive_query.sh q3_market_reach "reach_rank,market_reach_tier,track_count,avg_first_month_streams"

echo "Running q4..."
bash scripts/run_hive_query.sh q4_artist_maturity "maturity_rank,artist_maturity_tier,track_count,avg_first_month_streams"

echo "Running q5..."
bash scripts/run_hive_query.sh q5_artist_momentum "momentum_rank,artist_history_strength,track_count,avg_first_month_streams"

echo "Running q6..."
bash scripts/run_hive_query.sh q6_audio_profile "profile_rank,audio_profile,track_count,avg_first_month_streams"

echo "Validating Stage II outputs..."
python3 scripts/validate_stage2.py

echo "Running pylint..."
bash scripts/pylint_stage2.sh