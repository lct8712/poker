package com.wandoujia.poker.models;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

import lombok.Data;

import com.sun.tools.javac.util.Pair;

/**
 * @author chentian
 */
@Data
public class GameInfoBean {

    public static final double ERROR_EXCEPTED = 10.0;

    private Date date;

    private String comments;

    private List<Pair<String, Double>> players;

    public Double computeSum() {
        Double sum = 0.0;
        for (Pair<String, Double> player : players) {
            sum += player.snd;
        }
        return sum;
    }

    public boolean isValidate() {
        return computeSum() <= ERROR_EXCEPTED;
    }

    public static class DescComparator implements Comparator<GameInfoBean> {
        @Override
        public int compare(GameInfoBean gameInfoBean, GameInfoBean gameInfoBean2) {
            return gameInfoBean2.getDate().compareTo(gameInfoBean.getDate());
        }
    }
}
