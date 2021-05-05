package com.solactive.solactive_code_challenge.models;

import com.solactive.solactive_code_challenge.calculators.*;
import com.solactive.solactive_code_challenge.models.dtos.InstrumentStatistics;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.locks.ReentrantLock;

public class Instrument {
    private ReentrantLock mutex = new ReentrantLock();
    private String name;
    private Long time_horizon;
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

    Instrument(String name, Long time_horizon, Double lambdaExponentialDecay) {
        this.name = name;
        this.lambdaExponentialDecay = lambdaExponentialDecay;
        this.time_horizon = time_horizon;
    }

    public void addTick(Long timestep, Double price) {
        ticks.putIfAbsent(timestep, price);
    }

    public void getTickInWindow(Long actualTime, Long window) {
        this.ticks = new TreeMap<Long, Double>(this.ticks.tailMap(actualTime-window));
    }

    public void recalculationVariables() {
        this.statisticsCache = new TreeMap<Long, StatisticsOfInstrument>();

        // add here history cache
        StatisticsOfInstrument statistics = CalculateVariables(this.ticks);
        this.statisticsCache.put(this.ticks.lastKey(), statistics);

        for (Long key : this.ticks.keySet())
        {
            SortedMap<Long, Double> submap = this.ticks.tailMap(key + 1);
            TreeMap<Long, Double> subtreemap = new TreeMap<Long, Double>(submap);
            statistics = CalculateVariables(subtreemap);
            this.statisticsCache.put(key + getTimeHorizon() + 1 , statistics);
        }
    }

    StatisticsOfInstrument CalculateVariables(TreeMap<Long, Double> ticks) {
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

        StatisticsOfInstrument statistics = new StatisticsOfInstrument(ticks, getLambdaExponentialDecay());

        statistics.setMaximum(ExtremumCalculator.calculate(ticks, max));
        statistics.setMinimum(ExtremumCalculator.calculate(ticks, min));
        statistics.setCount(Long.valueOf(ticks.size()));
        statistics.setAverage(AverageCalculators.calculate(ticks));
        statistics.setVolatility(Math.sqrt(PositionIndependentAverageCalculator.calculate(ticks, sqr) - statistics.getAverage() * statistics.getAverage()));
        statistics.setMaximalDrawdown(MaximalDrawdown.calculate(ticks));
        statistics.setQuantile_5(QuantileCalculator.calculate(ticks, 0.05));
        statistics.setTimeWeightedAverage(WeightedGeneralizedAverageCalculator.calculate(ticks, timeAverage, average));
        statistics.setTimeExponentiallyWeightedAverage(WeightedGeneralizedAverageCalculator.calculate(ticks, exponentialDecay, average));
        return statistics;
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

    public Long getTimeHorizon() {
        return time_horizon;
    }
}
