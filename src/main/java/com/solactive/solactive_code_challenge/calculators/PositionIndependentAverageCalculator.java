package com.solactive.solactive_code_challenge.calculators;

import java.util.Map;
import java.util.TreeMap;

public class PositionIndependentAverageCalculator {

    public static Double calculate(TreeMap<Long, Double> ticks, SingleArgumentFunc averageFunc) {
        Double average = 0.0;
        for(Map.Entry<Long, Double> entry: ticks.entrySet()) {
            average += averageFunc.function(entry.getValue());
        }
        return average / ticks.size();
    }
}
