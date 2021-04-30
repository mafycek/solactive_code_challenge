package com.solactive.solactive_code_challenge.models.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InstrumentStatistics {

    @JsonProperty("avr") private Double average = Double.NaN;

    @JsonProperty("max") private Double maximum = Double.NaN;

    @JsonProperty("min") private Double minimum = Double.NaN;

    @JsonProperty("maxDrawdown") private Double maximalDrawdown = Double.NaN;

    @JsonProperty("volatility") private Double volatility = Double.NaN;

    @JsonProperty("quantile") private Double quantile_95 = Double.NaN;

    @JsonProperty("twap") private Double timeWeightedAverage = Double.NaN;

    @JsonProperty("twap2") private Double timeExponentiallyWeightedAverage = Double.NaN;

    @JsonProperty("count") private Long count = 0L;

    /* etc. */

    public void setAverage(Double average) {
        this.average = average;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public void setMaximalDrawdown(Double maximalDrawdown) {
        this.maximalDrawdown = maximalDrawdown;
    }

    public void setMaximum(Double maximum) {
        this.maximum = maximum;
    }

    public void setMinimum(Double minimum) {
        this.minimum = minimum;
    }

    public void setQuantile_95(Double quantile_95) {
        this.quantile_95 = quantile_95;
    }

    public void setTimeExponentiallyWeightedAverage(Double timeExponentiallyWeightedAverage) {
        this.timeExponentiallyWeightedAverage = timeExponentiallyWeightedAverage;
    }

    public void setTimeWeightedAverage(Double timeWeightedAverage) {
        this.timeWeightedAverage = timeWeightedAverage;
    }

    public void setVolatility(Double volatility) {
        this.volatility = volatility;
    }

    public Double getAverage() {
        return average;
    }

    public Double getMaximalDrawdown() {
        return maximalDrawdown;
    }

    public Double getMaximum() {
        return maximum;
    }

    public Double getMinimum() {
        return minimum;
    }

    public Double getQuantile_95() {
        return quantile_95;
    }

    public Double getTimeExponentiallyWeightedAverage() {
        return timeExponentiallyWeightedAverage;
    }

    public Double getTimeWeightedAverage() {
        return timeWeightedAverage;
    }

    public Double getVolatility() {
        return volatility;
    }

    public Long getCount() {
        return count;
    }

}
