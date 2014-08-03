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

    public static Comparator<PlayerDataBean> getSumComparator() {
        return new Comparator<PlayerDataBean>() {
            @Override
            public int compare(PlayerDataBean p1, PlayerDataBean p2) {
                return p1.getSum().compareTo(p2.getSum());
            }
        };
    }

    public static Comparator<PlayerDataBean> getCountComparator() {
        return new Comparator<PlayerDataBean>() {
            @Override
            public int compare(PlayerDataBean p1, PlayerDataBean p2) {
                return Integer.valueOf(p1.history.size()).compareTo(p2.history.size());
            }
        };
    }

    public static Comparator<PlayerDataBean> getMeanComparator() {
        return new Comparator<PlayerDataBean>() {
            @Override
            public int compare(PlayerDataBean p1, PlayerDataBean p2) {
                return p1.getMean().compareTo(p2.getMean());
            }
        };
    }

    public static Comparator<PlayerDataBean> getStdDevComparator() {
        return new Comparator<PlayerDataBean>() {
            @Override
            public int compare(PlayerDataBean p1, PlayerDataBean p2) {
                return p1.getStdDev().compareTo(p2.getStdDev());
            }
        };
    }
}
