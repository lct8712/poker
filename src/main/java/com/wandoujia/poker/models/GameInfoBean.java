package com.wandoujia.poker.models;

import com.google.gdata.util.common.base.Pair;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * @author chentian
 */
public class GameInfoBean {

    public static final double ERROR_EXCEPTED = 10.0;

    private Date date;

    private String comments;

    private List<Pair<String, Double>> players;

    public Double computeSum() {
        Double sum = 0.0;
        for (Pair<String, Double> player : players) {
            sum += player.getSecond();
        }
        return sum;
    }

    public boolean isValidate() {
        return Math.abs(computeSum()) <= ERROR_EXCEPTED;
    }

    public static class DescComparator implements Comparator<GameInfoBean> {
        @Override
        public int compare(GameInfoBean gameInfoBean, GameInfoBean gameInfoBean2) {
            return gameInfoBean2.getDate().compareTo(gameInfoBean.getDate());
        }
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public List<Pair<String, Double>> getPlayers() {
        return players;
    }

    public void setPlayers(List<Pair<String, Double>> players) {
        this.players = players;
    }

    @Override
    public String toString() {
        return "GameInfoBean{" +
                "date=" + date +
                ", comments='" + comments + '\'' +
                ", players=" + players +
                '}';
    }
}
