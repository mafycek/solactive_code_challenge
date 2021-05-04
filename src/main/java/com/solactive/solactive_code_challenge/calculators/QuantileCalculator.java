package com.solactive.solactive_code_challenge.calculators;

import java.util.*;

public class QuantileCalculator extends GenericWindowCalculator {

    public static Double calculate(Map<Long, Double> ticks, Double percentile) {
        ArrayList<Double> values = new ArrayList(ticks.values());
        Collections.sort(values);
        Double percentileIncrease = 1.0 / (ticks.size() - 1);
        Double actualPercentile = 0.0;
        if (values.size() == 1) {
            return values.get(0);
        }

        for (Iterator<Double> it = values.iterator(); it.hasNext(); ) {
            Double actualValue = it.next();
            if (actualPercentile + percentileIncrease > percentile) {
                it.hasNext();
                Double nextValue = it.next();

                Double quantile = (percentile - actualPercentile) * (nextValue - actualValue) / percentileIncrease + actualValue;
                return quantile;
            }
            actualPercentile += percentileIncrease;
        }
        return null;
    }
}
