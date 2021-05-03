package com.solactive.solactive_code_challenge.models;

import com.solactive.solactive_code_challenge.models.dtos.InstrumentStatistics;

public class StatisticsOfInstrument {
    private Double average = Double.NaN;

    private Double maximum = Double.NaN;

    private Double minimum = Double.NaN;

    private Double maximalDrawdown = Double.NaN;

    private Double volatility = Double.NaN;

    private Double quantile_5 = Double.NaN;

    private Double timeWeightedAverage = Double.NaN;

    private Double timeExponentiallyWeightedAverage = Double.NaN;

    private Long count = 0L;

    StatisticsOfInstrument() {
    }

    public Double getTimeWeightedAverage() {
        return timeWeightedAverage;
    }

    public Long getCount() {
        return count;
    }

    public Double getVolatility() {
        return volatility;
    }

    public Double getTimeExponentiallyWeightedAverage() {
        return timeExponentiallyWeightedAverage;
    }

    public Double getMinimum() {
        return minimum;
    }

    public Double getMaximum() {
        return maximum;
    }

    public Double getMaximalDrawdown() {
        return maximalDrawdown;
    }

    public Double getAverage() {
        return average;
    }

    public Double getQuantile_5() {
        return quantile_5;
    }

    public void setVolatility(Double volatility) {
        this.volatility = volatility;
    }

    public void setTimeWeightedAverage(Double timeWeightedAverage) {
        this.timeWeightedAverage = timeWeightedAverage;
    }

    public void setTimeExponentiallyWeightedAverage(Double timeExponentiallyWeightedAverage) {
        this.timeExponentiallyWeightedAverage = timeExponentiallyWeightedAverage;
    }

    public void setMinimum(Double minimum) {
        this.minimum = minimum;
    }

    public void setMaximum(Double maximum) {
        this.maximum = maximum;
    }

    public void setMaximalDrawdown(Double maximalDrawdown) {
        this.maximalDrawdown = maximalDrawdown;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public void setAverage(Double average) {
        this.average = average;
    }

    public void setQuantile_5(Double quantile_5) {
        this.quantile_5 = quantile_5;
    }

    InstrumentStatistics getStatistics() {
        InstrumentStatistics statistics = new InstrumentStatistics();

        statistics.setAverage(average);
        statistics.setCount(count);
        statistics.setMaximum(maximum);
        statistics.setMinimum(minimum);
        statistics.setQuantile_5(quantile_5);
        statistics.setVolatility(volatility);
        statistics.setMaximalDrawdown(maximalDrawdown);
        statistics.setTimeWeightedAverage(timeWeightedAverage);
        statistics.setTimeExponentiallyWeightedAverage(timeExponentiallyWeightedAverage);

        return statistics;
    }
}
