package com.wandoujia.poker.models;

import java.util.ArrayList;
import java.util.Comparator;
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

        sum = StatisticsUtil.computeSum(history);
        mean = StatisticsUtil.computeMean(history);
        stdDev = StatisticsUtil.computeStandardDeviation(history);
    }

    public static class SumComparator implements Comparator<PlayerDataBean> {
        @Override
        public int compare(PlayerDataBean p1, PlayerDataBean p2) {
            return p1.getSum().compareTo(p2.getSum());
        }
    }

    public static class CountComparator implements Comparator<PlayerDataBean> {
        @Override
        public int compare(PlayerDataBean p1, PlayerDataBean p2) {
            return Integer.valueOf(p1.history.size()).compareTo(p2.history.size());
        }
    }

    public static class MeanComparator implements Comparator<PlayerDataBean> {
        @Override
        public int compare(PlayerDataBean p1, PlayerDataBean p2) {
            return p1.getMean().compareTo(p2.getMean());
        }
    }

    public static class StdDevComparator implements Comparator<PlayerDataBean> {
        @Override
        public int compare(PlayerDataBean p1, PlayerDataBean p2) {
            return p1.getStdDev().compareTo(p2.getStdDev());
        }
    }
}
