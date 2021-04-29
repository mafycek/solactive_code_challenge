package com.solactive.solactive_code_challenge.models.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class instrumentStatistics {

    @JsonProperty("avr") private String average;

    @JsonProperty("max") private String maximum;

    @JsonProperty("min") private String minimum;

    @JsonProperty("maxDrawdown") private String maximalDrawdown;

    @JsonProperty("volatility") private String volatility;

    @JsonProperty("quantile") private String quantile_95;

    @JsonProperty("twap") private String timeWeightedAverage;

    @JsonProperty("twap2") private String timeExponentiallyWeightedAverage;

    @JsonProperty("count") private String count;

    /* etc. */

    public void setAverage(String average) {
        this.average = average;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public void setMaximalDrawdown(String maximalDrawdown) {
        this.maximalDrawdown = maximalDrawdown;
    }

    public void setMaximum(String maximum) {
        this.maximum = maximum;
    }

    public void setMinimum(String minimum) {
        this.minimum = minimum;
    }

    public void setQuantile_95(String quantile_95) {
        this.quantile_95 = quantile_95;
    }

    public void setTimeExponentiallyWeightedAverage(String timeExponentiallyWeightedAverage) {
        this.timeExponentiallyWeightedAverage = timeExponentiallyWeightedAverage;
    }

    public void setTimeWeightedAverage(String timeWeightedAverage) {
        this.timeWeightedAverage = timeWeightedAverage;
    }

    public void setVolatility(String volatility) {
        this.volatility = volatility;
    }
}
