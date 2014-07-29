package com.wandoujia.poker.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sun.tools.javac.util.Pair;
import com.wandoujia.poker.dao.DataDao;
import com.wandoujia.poker.models.GameInfoBean;
import com.wandoujia.poker.models.PlayerDataBean;

/**
 * @author chentian
 */
@Service
public class PokerServiceImpl implements PokerService {

    @Autowired
    private DataDao dataDao;

    private List<GameInfoBean> gameInfoBeans = new ArrayList<GameInfoBean>();
    private Map<String, PlayerDataBean> playerDataBeans = new HashMap<String, PlayerDataBean>();

    @Override
    public void reloadData() {
        gameInfoBeans = dataDao.loadGameInfos();
        parsePlayers();
    }

    @Override
    public List<GameInfoBean> getGameInfoBeans() {
        return gameInfoBeans;
    }

    @Override
    public Map<String, PlayerDataBean> getPlayerDataBeans() {
        return playerDataBeans;
    }

    private void parsePlayers() {
        playerDataBeans = new HashMap<String, PlayerDataBean>();
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
        if (playerDataBeans.containsKey(pair.fst)) {
            player = playerDataBeans.get(pair.fst);
        } else {
            player = new PlayerDataBean(pair.fst);
        }

        List<Double> history = player.getHistory();
        history.add(pair.snd);
        player.setHistory(history);

        playerDataBeans.put(pair.fst, player);
    }

    public void setDataDao(DataDao dataDao) { this.dataDao = dataDao; }
}
