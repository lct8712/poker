package com.wandoujia.poker.models;

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
}
