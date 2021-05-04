package com.solactive.solactive_code_challenge.models;

import com.solactive.solactive_code_challenge.calculators.*;
import com.solactive.solactive_code_challenge.models.dtos.InstrumentStatistics;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.locks.ReentrantLock;

public class Instrument {

    private ReentrantLock mutex = new ReentrantLock();

    private String name;

    private Double lambdaExponentialDecay;

    TreeMap<Long, Double> ticks = new TreeMap<Long, Double>();

    TreeMap<Long, StatisticsOfInstrument> statisticsCache = new TreeMap<Long, StatisticsOfInstrument>();

    public InstrumentStatistics getStatistics(Long actualTimestamp) {
        try {
            this.getMutex().lock();

            Map.Entry<Long, StatisticsOfInstrument> previousEntry = this.statisticsCache.floorEntry(actualTimestamp);
            InstrumentStatistics statistics = previousEntry.getValue().getStatistics(actualTimestamp);
            statistics.setInstrument(name);

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

        // add here history cache

        StatisticsOfInstrument statistics = new StatisticsOfInstrument(ticks, getLambdaExponentialDecay());
        statistics.setMaximum(ExtremumCalculator.calculate(this.ticks, max));
        statistics.setMinimum(ExtremumCalculator.calculate(this.ticks, min));
        statistics.setCount(Long.valueOf(this.ticks.size()));
        statistics.setAverage(AverageCalculators.calculate(this.ticks));
        statistics.setVolatility(Math.sqrt(PositionIndependentAverageCalculator.calculate(this.ticks, sqr) - statistics.getAverage() * statistics.getAverage()));
        statistics.setMaximalDrawdown(MaximalDrawdown.calculate(this.ticks));
        statistics.setQuantile_5(QuantileCalculator.calculate(this.ticks, 0.05));
        statistics.setTimeWeightedAverage(WeightedGeneralizedAverageCalculator.calculate(this.ticks, timeAverage, average));
        statistics.setTimeExponentiallyWeightedAverage(WeightedGeneralizedAverageCalculator.calculate(this.ticks, exponentialDecay, average));

        statisticsCache.put(0L , statistics);
    }

    public ReentrantLock getMutex() {
        return mutex;
    }

    public TreeMap<Long, Double> getTicks() {
        return ticks;
    }

    public Double getLambdaExponentialDecay() {
        return lambdaExponentialDecay;
    }
}
