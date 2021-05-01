package com.solactive.solactive_code_challenge.calculators;

import java.util.Map;

public class AverageCalculators extends GenericWindowCalculator{
    public static Double calculate(Map<Long, Double> ticks) {
        Double sumOfWeightedValues = 0.0;
        Double sumWeights = 0.0;
        for(Map.Entry<Long, Double> entry: ticks.entrySet()) {
            Double weight = 1.0;

            sumOfWeightedValues += weight * entry.getValue();
            sumWeights += weight;
        }
        return sumOfWeightedValues / sumWeights;
    }

}
