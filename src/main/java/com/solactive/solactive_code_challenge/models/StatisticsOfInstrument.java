package com.solactive.solactive_code_challenge.models;

import com.solactive.solactive_code_challenge.models.dtos.InstrumentStatistics;
import org.testng.internal.collections.Pair;

import java.util.SortedMap;

public class StatisticsOfInstrument {
    SortedMap<Long, Double> ticks;

    private Double lambdaExponentialDecay;

    private Double average = Double.NaN;
    private Double maximum = Double.NaN;
    private Double minimum = Double.NaN;
    private Double maximalDrawdown = Double.NaN;
    private Double volatility = Double.NaN;
    private Double quantile_5 = Double.NaN;
    private Pair<Double, Double> timeWeightedAverage = new Pair(0.0, 0.0);
    private Pair<Double, Double> timeExponentiallyWeightedAverage = new Pair(0.0, 0.0);
    private Long count = 0L;

    StatisticsOfInstrument(SortedMap<Long, Double> ticks, Double lambdaExponentialDecay) {
        this.ticks = ticks;
        this.lambdaExponentialDecay = lambdaExponentialDecay;
    }

    public Pair<Double, Double> getTimeWeightedAverage() {
        return timeWeightedAverage;
    }

    public Long getCount() {
        return count;
    }

    public Double getVolatility() {
        return volatility;
    }

    public Pair<Double, Double> getTimeExponentiallyWeightedAverage() {
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

    public void setTimeWeightedAverage(Pair timeWeightedAverage) {
        this.timeWeightedAverage = timeWeightedAverage;
    }

    public void setTimeExponentiallyWeightedAverage(Pair timeExponentiallyWeightedAverage) {
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

    InstrumentStatistics getStatistics(Long actualTimestamp) {
        InstrumentStatistics statistics = new InstrumentStatistics();
        if (this.ticks.size() == 0)
        {
            return statistics;
        }
        else {
            Long lastKey = this.ticks.lastKey();
            Double lastValue = this.ticks.get(lastKey);
            Long timeWeight = (actualTimestamp - lastKey);
            Double compensationToTimeWeightedAverage = timeWeight * lastValue;
            Double timeExponentialWeight = Math.exp(-getLambdaExponentialDecay() * timeWeight);
            Double compensationToTimeExponentialWeightedAverage = timeExponentialWeight * lastValue;

            statistics.setAverage(average);
            statistics.setCount(count);
            statistics.setMaximum(maximum);
            statistics.setMinimum(minimum);
            statistics.setQuantile_5(quantile_5);
            statistics.setVolatility(volatility);
            statistics.setMaximalDrawdown(maximalDrawdown);
            statistics.setTimeWeightedAverage((getTimeWeightedAverage().first() + compensationToTimeWeightedAverage) / (getTimeWeightedAverage().second() + timeWeight));
            statistics.setTimeExponentiallyWeightedAverage((getTimeExponentiallyWeightedAverage().first() + compensationToTimeExponentialWeightedAverage) / (getTimeExponentiallyWeightedAverage().second() + timeExponentialWeight));

            return statistics;
        }
    }

    public Double getLambdaExponentialDecay() {
        return lambdaExponentialDecay;
    }
}
