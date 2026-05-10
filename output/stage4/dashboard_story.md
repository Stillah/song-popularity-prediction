# Song Popularity Prediction Dashboard

The dashboard presents the project pipeline from data preparation to business
recommendations. The target variable is first-month Spotify streams.

## Main Findings

- Artist history is the strongest signal for future stream performance.
- Friday releases and wider market availability are connected with stronger launches.
- Audio features add useful context, but they are weaker predictors than artist and release factors.
- GBT Regressor is the best model because it captures non-linear effects better than Linear Regression.

## Business Recommendation

Release planning should prioritize artist momentum, launch timing, and market
coverage. The model can support early expectations for new tracks, but viral
effects and playlist placement remain outside the available feature set.
