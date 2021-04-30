package com.solactive.solactive_code_challenge.models;

import com.solactive.solactive_code_challenge.models.dtos.InstrumentStatistics;

import java.util.Map;

public class Instrument {

    String name;

    Map<Long, Double> ticks;

    private Double average = Double.NaN;

    private Double maximum = Double.NaN;

    private Double minimum = Double.NaN;

    private Double maximalDrawdown = Double.NaN;

    private Double volatility = Double.NaN;

    private Double quantile_95 = Double.NaN;

    private Double timeWeightedAverage = Double.NaN;

    private Double timeExponentiallyWeightedAverage = Double.NaN;

    private Long count = 0L;

    public InstrumentStatistics getStatistics() {
        InstrumentStatistics statistics = new InstrumentStatistics();
        statistics.setAverage(average);
        statistics.setCount(count);
        statistics.setMaximum(maximum);
        statistics.setMinimum(minimum);
        statistics.setQuantile_95(quantile_95);
        statistics.setVolatility(volatility);
        statistics.setMaximalDrawdown(maximalDrawdown);
        statistics.setTimeWeightedAverage(timeWeightedAverage);
        statistics.setTimeExponentiallyWeightedAverage(timeExponentiallyWeightedAverage);
        return statistics;
    }

    Instrument(String name) {
        this.name = name;
    }

}
