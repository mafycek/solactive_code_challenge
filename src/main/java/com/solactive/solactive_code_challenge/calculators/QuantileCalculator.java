package com.solactive.solactive_code_challenge.calculators;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public class QuantileCalculator extends GenericWindowCalculator {

    public static Double calculate(Map<Long, Double> ticks, Double percentile) {
        Collection<Double> values = ticks.values();
        Double percentileIncrease = 1.0 / ticks.size();
        Double actualPercentile = 0.0;
        if (values.size() == 1) {
            return (Double) values.toArray()[0];
        }

        for (Iterator<Double> it = values.iterator(); it.hasNext(); ) {
            Double actualValue = it.next();
            if (actualPercentile + percentileIncrease > percentile) {
                Double nextValue = it.next();

                Double quantile = (percentile - actualPercentile) * (nextValue - actualValue) / percentileIncrease + actualValue;
                return quantile;
            }
            actualPercentile += percentileIncrease;
        }
        return null;
    }
}
