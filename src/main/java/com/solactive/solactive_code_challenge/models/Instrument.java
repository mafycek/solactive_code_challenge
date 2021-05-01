package com.solactive.solactive_code_challenge.models;

import com.solactive.solactive_code_challenge.calculators.DoubleArgumentFunc;
import com.solactive.solactive_code_challenge.calculators.MaximumCalculator;
import com.solactive.solactive_code_challenge.models.dtos.InstrumentStatistics;

import java.util.HashMap;
import java.util.Map;

public class Instrument {

    String name;

    Map<Long, Double> ticks = new HashMap<Long, Double>();

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

    public void addTick(Long timestep, Double price) {
        ticks.putIfAbsent(timestep, price);
    }

    public void getTickInWindow(Long actualTime, Long window) {
        for(Map.Entry<Long, Double> entry : this.ticks.entrySet()) {
            if (actualTime - entry.getKey() > window) {
                // remove keys outside of the window
                this.ticks.remove(entry.getKey());
            }
        }
    }

    public void recalculationVariables() {
        DoubleArgumentFunc max = (value1, value2) -> {
            if (value1.isNaN()) {
                return true;
            } else {
                return value1 < value2;
            }
        };
        DoubleArgumentFunc min = (value1, value2) -> {
            if (value1.isNaN()) {
                return true;
            } else {
                return value1 > value2;
            }
        };

        this.maximum = MaximumCalculator.calculate(this.ticks, max);
        this.minimum = MaximumCalculator.calculate(this.ticks, min);
    }
}
