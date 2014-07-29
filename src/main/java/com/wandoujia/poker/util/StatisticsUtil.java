package com.wandoujia.poker.util;

import java.util.List;

/**
 * @author chentian
 */
public class StatisticsUtil {
    public static Double computeSum(List<Double> data) {
        Double result = 0.0;
        for (Double num : data) {
            result += num;
        }
        return result;
    }

    public static Double computeMean(List<Double> data) {
        if (data.isEmpty()) {
            throw new IllegalArgumentException();
        }

        return round(computeSum(data) / data.size());
    }

    public static Double computeVariance(List<Double> data) {
        double mean = computeMean(data);
        double sum = 0.0;
        for(double num : data) {
            sum += Math.pow(mean - num, 2);
        }
        return round(sum / data.size());
    }

    public static Double computeStandardDeviation(List<Double> data) {
        return round(Math.sqrt(computeVariance(data)));
    }

    // Keep 2 digits for num
    private static Double round(double num) {
        int precision = 100;
        return Math.floor(num * precision +.5)/precision;
    }
}
