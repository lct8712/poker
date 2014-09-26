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
    private Date date;
    private String comments;
    private List<Pair<String, Double>> players;


    public static class DescComparator implements Comparator<GameInfoBean> {
        @Override
        public int compare(GameInfoBean gameInfoBean, GameInfoBean gameInfoBean2) {
            return gameInfoBean2.getDate().compareTo(gameInfoBean.getDate());
        }
    }
}
