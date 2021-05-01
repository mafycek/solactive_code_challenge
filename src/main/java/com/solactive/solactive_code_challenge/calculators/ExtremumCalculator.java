package com.solactive.solactive_code_challenge.calculators;

import java.util.Map;

public class ExtremumCalculator extends GenericWindowCalculator{

    public static Double calculate(Map<Long, Double> ticks, DoubleArgumentFunc lambdaFunction) {
        Double extrem = Double.NaN;
        for(Map.Entry<Long, Double> entry: ticks.entrySet()) {
            if (lambdaFunction.function(extrem, entry.getValue())) {
                extrem = entry.getValue();
            }
        }
        return extrem;
    }
}
