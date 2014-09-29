package com.wandoujia.poker.models;

import com.wandoujia.poker.util.StatisticsUtil;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author chentian
 */
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Double> getHistory() {
        return history;
    }

    public void setHistory(List<Double> history) {
        this.history = history;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    public Double getMean() {
        return mean;
    }

    public void setMean(Double mean) {
        this.mean = mean;
    }

    public Double getStdDev() {
        return stdDev;
    }

    public void setStdDev(Double stdDev) {
        this.stdDev = stdDev;
    }

    @Override
    public String toString() {
        return "PlayerDataBean{" +
                "name='" + name + '\'' +
                ", history=" + history +
                ", sum=" + sum +
                ", mean=" + mean +
                ", stdDev=" + stdDev +
                '}';
    }
}
