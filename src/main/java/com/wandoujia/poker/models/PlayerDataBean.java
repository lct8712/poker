package com.wandoujia.poker.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.Data;

import com.wandoujia.poker.util.StatisticsUtil;

/**
 * @author chentian
 */
@Data
public class PlayerDataBean {
    private String name;
    private List<Double> history = new ArrayList<Double>();
    private Double sum;
    private Double mean;
    private Double stdDev;

    public PlayerDataBean(String name) {
        this.name = name;
    }

    public void compute() {
        if (history.isEmpty()) {
            return;
        }

        Collections.sort(history);
        Collections.reverse(history);
        sum = StatisticsUtil.computeSum(history);
        mean = StatisticsUtil.computeMean(history);
        stdDev = StatisticsUtil.computeStandardDeviation(history);
    }
}
