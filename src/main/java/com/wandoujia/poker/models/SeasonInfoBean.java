package com.wandoujia.poker.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;

import com.google.gdata.util.common.base.Pair;

/**
 * @author chentian
 */
@Data
public class SeasonInfoBean {

    private String seasonNumber;

    private List<GameInfoBean> gameInfoBeans;

    private Map<String, PlayerDataBean> playerDataBeans;

    public SeasonInfoBean(String seasonNumber) {
        this.seasonNumber = seasonNumber;

        this.gameInfoBeans = new ArrayList<>();
        this.playerDataBeans = new HashMap<>();
    }

    public void parsePlayers() {
        playerDataBeans = new HashMap<>();
        for (GameInfoBean gameInfo : gameInfoBeans) {
            for (Pair<String, Double> pair : gameInfo.getPlayers()) {
                updatePlayer(pair);
            }
        }
        for (PlayerDataBean playerDataBean : playerDataBeans.values()) {
            playerDataBean.compute();
        }
    }

    private void updatePlayer(Pair<String, Double> pair) {
        PlayerDataBean player;
        if (playerDataBeans.containsKey(pair.getFirst())) {
            player = playerDataBeans.get(pair.getFirst());
        } else {
            player = new PlayerDataBean(pair.getFirst());
        }

        List<Double> history = player.getHistory();
        history.add(pair.getSecond());
        player.setHistory(history);

        playerDataBeans.put(pair.getFirst(), player);
    }
}
