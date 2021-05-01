package com.solactive.solactive_code_challenge.calculators;

import java.util.Map;
import java.util.TreeMap;

public class WeightedGeneralizedAverageCalculator extends GenericWindowCalculator {

    public static Double calculate(TreeMap<Long, Double> ticks, LongWeightFunc weightFunction, SingleArgumentFunc averageFunc) {
        Double average = 0.0;
        Double sumWeights = 0.0;
        for(Map.Entry<Long, Double> entry: ticks.entrySet()) {
            Map.Entry<Long, Double> nextEntry = ticks.higherEntry(entry.getKey());
            if (nextEntry != null) {
                Double weight = weightFunction.function(entry.getKey(), nextEntry.getKey());
                sumWeights += weight;
                average += averageFunc.function(entry.getValue()) * weight;
            }
        }
        return average / sumWeights;
    }
}
