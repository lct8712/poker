package com.wandoujia.poker.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;

import com.google.gdata.util.common.base.Pair;
import com.wandoujia.poker.util.DateUtil;

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
            String date = DateUtil.DATE_FORMATTER.format(gameInfo.getDate());
            for (Pair<String, Double> pair : gameInfo.getPlayers()) {
                updatePlayer(pair.getFirst(), pair.getSecond(), date);
            }
        }
        for (PlayerDataBean playerDataBean : playerDataBeans.values()) {
            playerDataBean.compute();
        }
    }

    private void updatePlayer(String name, Double money, String date) {
        PlayerDataBean player;
        if (playerDataBeans.containsKey(name)) {
            player = playerDataBeans.get(name);
        } else {
            player = new PlayerDataBean(name);
        }

        player.getHistoryDate().add(date);
        player.getHistoryMoney().add(money);

        playerDataBeans.put(name, player);
    }
}
