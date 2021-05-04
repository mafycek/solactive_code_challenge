package com.solactive.solactive_code_challenge.calculators;

import java.util.Map;
import java.util.TreeMap;

public class MaximalDrawdown {
    public static Double calculate(TreeMap<Long, Double> ticks) {
        Double maxDrawdown = 0.0;
        if (ticks.size() == 0) {
            return Double.NaN;
        } else {
            Double maximum = ticks.firstEntry().getValue();
            for (Map.Entry<Long, Double> entries : ticks.entrySet()) {
                Double actualDrawdown = maximum - entries.getValue();
                if (actualDrawdown > maxDrawdown) {
                    maxDrawdown = actualDrawdown;
                }
                if (entries.getValue() > maximum) {
                    maximum = entries.getValue();
                }
            }

            return maxDrawdown;
        }
    }
}
