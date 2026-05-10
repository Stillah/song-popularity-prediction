SELECT 'Total rows:' AS check_name, COUNT(*) AS value FROM songs;

SELECT 'Min streams (first month):' AS check_name, MIN(streams_in_the_first_month) AS value FROM songs;
SELECT 'Max streams (first month):' AS check_name, MAX(streams_in_the_first_month) AS value FROM songs;
SELECT 'Avg streams (first month):' AS check_name, AVG(streams_in_the_first_month) AS value FROM songs;

SELECT 'NULLs in af_danceability:' AS check_name, COUNT(*) AS value FROM songs WHERE af_danceability IS NULL;
SELECT 'NULLs in streams_in_the_first_month:' AS check_name, COUNT(*) AS value FROM songs WHERE streams_in_the_first_month IS NULL;
SELECT 'NULLs in explicit:' AS check_name, COUNT(*) AS value FROM songs WHERE explicit IS NULL;
SELECT 'NULLs in artist_prior_tracks_count:' AS check_name, COUNT(*) AS value FROM songs WHERE artist_prior_tracks_count IS NULL;

SELECT * FROM songs LIMIT 5;