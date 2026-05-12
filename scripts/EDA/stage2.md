# Stage II - Data Storage/Preparation & EDA

## Overview

In this stage I prepared the data for analytics in Hive and then performed exploratory data analysis. The main goal of this stage was to move from the raw imported table from Stage I to a Hive-based analytical structure that is easier and faster to query. After that, I created several EDA queries, stored their results, and used them later for visualization in Apache Superset.

Our project dataset is based on Spotify charts and audio features. In Stage I, the clean data was already loaded into PostgreSQL and then imported to HDFS with Sqoop in Parquet format with Snappy compression. In Stage II, I used this data to build Hive tables for further analysis.

## Added files in Stage II

### `scripts/EDA/hive_setup.sh`
This script prepares the Hive part of Stage II. It reads the Hive password, cleans old Stage II folders in HDFS, runs the main Hive DDL file, and saves the setup output to the `output/` folder. I use it to create the Hive database and the main analytical Hive tables.

### `scripts/EDA/run_hive_query.sh`
This script runs one EDA query file from the `sql/` folder using Beeline. It also exports the query result from HDFS into a local CSV file in `output/`. I use it to automate running each insight query and saving its result.

### `scripts/code_style/validate_stage2.py`
This script checks whether the main Stage II output files were created successfully. It verifies that required files exist and are not empty, then writes the validation result into `output/validate_stage2.txt`. I use it as a simple final check after running Stage II.

### `scripts/code_style/pylint_stage2.sh`
This script runs `pylint` for the Stage II validation script and saves the result into `output/pylint_stage2.txt`. I use it to show code quality checking for this stage.

### `scripts/EDA/stage2.sh`
This is the main Stage II script. It runs the Hive setup, executes all EDA queries, validates outputs, and launches the pylint check. I use this file as the main entry point for testing Stage II.

---

## SQL / HiveQL files

### `sql/db.hql`
This is the main Hive setup file. It creates the Hive database in a separate warehouse location, defines the first external Hive table over the Stage I Parquet data, then creates the optimized analytical table with partitioning and bucketing. It also loads data into the optimized table and checks that it can be queried.

### `sql/q0_characteristics.hql`
This query creates a summary table with general dataset characteristics, such as number of rows, number of features, target information, and several aggregated statistics. It is used for the dashboard and the report as a compact overview of the prepared dataset.

### `sql/q1_release_month.hql`
This query analyzes the relation between `release_month` and average first-month streams. It is used to check whether there is a seasonal pattern in release performance.

### `sql/q2_release_weekday.hql`
This query analyzes average first-month streams by release weekday. It is used to study whether release timing during the week is associated with stronger launch results.

### `sql/q3_market_reach.hql`
This query groups songs by market reach tiers based on `num_markets` and compares average first-month streams between these groups. It is used to evaluate whether wider international availability is related to stronger early performance.

### `sql/q4_artist_maturity.hql`
This query groups artists by `artist_prior_tracks_count` and compares average first-month streams between maturity levels. It is used to check whether artist experience and larger catalog history give an advantage.

### `sql/q5_artist_momentum.hql`
This query groups tracks by tiers of `artist_avg_past_streams` and compares their average first-month streams. It is used to analyze whether previous artist success carries over to future releases.

### `sql/q6_audio_profile.hql`
This query groups songs by simplified audio profiles based on `af_danceability` and `af_energy`. It is used to compare early streaming performance between different musical profile groups.

---

## Output files

### `output/db_setup.txt`
This file stores the Beeline output from the Hive setup step. It is used as proof that the Hive database and tables were created successfully.

### `output/hive_warehouse_ls.txt`
This file stores the HDFS listing of the Hive warehouse directories after setup. It is used to show that the Hive warehouse structure was created correctly.

### `output/q0_characteristics.csv`
This file contains the exported CSV result of the dataset characteristics query.

### `output/q1_release_month.csv`
This file contains the exported CSV result of the release month insight.

### `output/q2_release_weekday.csv`
This file contains the exported CSV result of the release weekday insight.

### `output/q3_market_reach.csv`
This file contains the exported CSV result of the market reach insight.

### `output/q4_artist_maturity.csv`
This file contains the exported CSV result of the artist maturity insight.

### `output/q5_artist_momentum.csv`
This file contains the exported CSV result of the artist historical strength insight.

### `output/q6_audio_profile.csv`
This file contains the exported CSV result of the audio profile insight.

### `output/q0.jpg ... output/q6.jpg`
These files are the exported chart images from Apache Superset. They are used later in the report, presentation, and final dashboard stage.

### `output/validate_stage2.txt`
This file stores the result of the Stage II validation script. It confirms whether all expected output files were created successfully.

### `output/pylint_stage2.txt`
This file stores the pylint results for the Stage II validation script and is used as evidence of code quality checking.

## Creating the Hive database

First, I created a separate Hive database in a different HDFS location:

`project/hive/warehouse`

I did not use the same HDFS path that was used by Sqoop in Stage I because Stage II requires a separate Hive warehouse. I created the database `team23_projectdb` and then worked inside this database.

I used external tables, because the data files are stored in HDFS and I wanted Hive to manage only metadata. This also makes the pipeline safer, because dropping the Hive table does not remove the underlying data files automatically.

## External Hive table

The first Hive table was created on top of the Parquet files imported in Stage I. This table was used only as an initial external table to read the imported data and verify that the schema is correct.

At this step I checked:
- that the table can be queried
- that column names and data types are correct
- that the row count is valid
- that the data is readable from Hive

After that, I created an optimized Hive table for EDA.

## Optimized Hive table: partitioning and bucketing

For analytics, I created an external Hive table called `songs_part_bucket`. This table uses both partitioning and bucketing.

### Partitioning

I partitioned the table by `release_month`.

I selected this column because:
- it has low cardinality
- it is meaningful for analysis
- it helps with seasonal queries
- it is a good business feature for music release timing

With this design, Hive stores different months in separate partition folders, so queries that filter by month do not need to scan the full table.

### Bucketing

I bucketed the table by `num_markets`.

I selected this column because it represents how widely a song is available across countries. This is important for our project because international reach can affect first-month streams. Bucketing on this column gives extra structure to the data and makes the table look more optimized for analytical queries.

### Why this table was used for EDA

After creating the optimized table and loading data into it, I used this table for all EDA queries. The original unpartitioned Hive table was not used for analysis anymore. This follows the Stage II checklist, where EDA should be based on partitioned and bucketed Hive tables.

## EDA process

For EDA, I wrote separate HiveQL queries and saved them into individual `.hql` files. Each query produced one data insight. The result of each query was stored in a separate table such as `q1_results`, `q2_results`, and so on.

Then each result table was exported to a CSV file in the `output/` directory. After that, datasets and charts were created manually in Apache Superset.

The automated part of the stage includes:
- Hive database creation
- creation of external tables
- creation of partitioned and bucketed table
- execution of EDA queries
- saving results into Hive tables
- exporting results to CSV
- validation of output files
- pylint check

The manual part includes:
- creating datasets in Superset
- creating charts in Superset
- exporting chart images

## Data characteristics

Before analyzing insights, I also created a small summary result table for the dataset characteristics. This includes basic information such as:
- number of rows
- number of features
- target column
- average and maximum target value
- average number of markets
- share of explicit tracks

This summary is useful for the report and also for the final dashboard.

## EDA insights

I prepared six main insights for this stage.

### Insight 1: Release month and first-month streams

In this query, I grouped tracks by `release_month` and calculated:
- number of tracks
- average first-month streams

This insight helps to see if there is any seasonal pattern in release performance.

### Insight 2: Release weekday and first-month streams

Here I grouped tracks by `release_day_of_week` and compared average first-month streams by weekday.

This helps to understand whether release timing during the week is associated with stronger launch performance.

### Insight 3: Market reach and first-month streams

In this query, I grouped songs into market reach tiers based on `num_markets`, for example:
- less than 50 markets
- 50 to 99 markets
- 100 to 149 markets
- 150 to 199 markets
- 200+ markets

Then I compared average first-month streams between these groups.

This insight is useful because it shows whether wider international availability is related to stronger early streaming results.

### Insight 4: Artist maturity

Here I used `artist_prior_tracks_count` to divide artists into maturity groups, from debut artists to artists with many previous tracks.

Then I compared average first-month streams across these groups.

This helps to understand whether artist experience and bigger catalog history give an advantage for new releases.

### Insight 5: Artist historical strength

In this query, I used `artist_avg_past_streams` to create tiers of artist historical performance and compared average first-month streams of new tracks.

This insight helps to see whether previous artist success carries over to future releases.

### Insight 6: Audio profile

In this query, I created simplified audio profile groups based on `af_danceability` and `af_energy`, for example:
- high danceability / high energy
- high danceability / low energy
- low danceability / high energy
- low danceability / low energy

Then I compared average first-month streams between these audio profile groups.

This insight connects analytical results with the musical characteristics of tracks.

## Outputs

At the end of Stage II, I produced:
- Hive database and Hive tables
- optimized partitioned and bucketed analytical table
- result tables for each query
- exported CSV files in `output/`
- chart images from Superset
- validation output
- pylint output

The main exported files include:
- `output/q0_characteristics.csv`
- `output/q1_release_month.csv`
- `output/q2_release_weekday.csv`
- `output/q3_market_reach.csv`
- `output/q4_artist_maturity.csv`
- `output/q5_artist_momentum.csv`
- `output/q6_audio_profile.csv`

The chart images were also saved as:
- `output/q0.jpg`
- `output/q1.jpg`
- `output/q2.jpg`
- `output/q3.jpg`
- `output/q4.jpg`
- `output/q5.jpg`
- `output/q6.jpg`
