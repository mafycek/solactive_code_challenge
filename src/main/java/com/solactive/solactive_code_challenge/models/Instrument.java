package com.solactive.solactive_code_challenge.models;

import com.solactive.solactive_code_challenge.calculators.*;
import com.solactive.solactive_code_challenge.models.dtos.InstrumentStatistics;
import org.springframework.beans.factory.annotation.Value;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.locks.ReentrantLock;

public class Instrument {

    @Value("${solactive.lambda}")
    private Long lambda;

    private ReentrantLock mutex = new ReentrantLock();

    String name;

    Double lambdaExponentialDecay;

    TreeMap<Long, Double> ticks = new TreeMap<Long, Double>();

    private Double average = Double.NaN;

    private Double maximum = Double.NaN;

    private Double minimum = Double.NaN;

    private Double maximalDrawdown = Double.NaN;

    private Double volatility = Double.NaN;

    private Double quantile_5 = Double.NaN;

    private Double timeWeightedAverage = Double.NaN;

    private Double timeExponentiallyWeightedAverage = Double.NaN;

    private Long count = 0L;

    public InstrumentStatistics getStatistics() {
        InstrumentStatistics statistics = new InstrumentStatistics();
        try {
            this.getMutex().lock();

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
        } finally {
            this.getMutex().unlock();
        }
    }

    Instrument(String name, Double lambdaExponentialDecay) {
        this.name = name;
        this.lambdaExponentialDecay = lambdaExponentialDecay;
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
        SingleArgumentFunc sqr = (value) -> {
            return value*value;
        };

        SingleArgumentFunc average = (value) -> {
            return value;
        };

        LongWeightFunc timeAverage = (value1, value2) -> {
            Double distance = Double.valueOf(value2-value1);
            return distance;

        };

        LongWeightFunc exponentialDecay = (value1, value2) -> {
            Double distance = Double.valueOf(value2-value1);
            return Math.exp(-distance * this.lambdaExponentialDecay);
        };

        this.maximum = ExtremumCalculator.calculate(this.ticks, max);
        this.minimum = ExtremumCalculator.calculate(this.ticks, min);
        this.count = Long.valueOf(this.ticks.size());
        this.average = AverageCalculators.calculate(this.ticks);
        this.volatility = Math.sqrt(PositionIndependentAverageCalculator.calculate(this.ticks, sqr) - this.average * this.average);
        this.maximalDrawdown = MaximalDrawdown.calculate(this.ticks);
        this.quantile_5 = QuantileCalculator.calculate(this.ticks, 0.05);
        this.timeWeightedAverage = WeightedGeneralizedAverageCalculator.calculate(this.ticks, timeAverage, average);
        this.timeExponentiallyWeightedAverage = WeightedGeneralizedAverageCalculator.calculate(this.ticks, exponentialDecay, average);
    }

    public ReentrantLock getMutex() {
        return mutex;
    }
}
