package com.wandoujia.poker.service;

import java.util.*;

import org.apache.commons.lang.StringUtils;
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

    enum RankingType {
        sum, count, mean, stddev
    }

    @Override
    public boolean reloadData() {
        gameInfoBeans = dataDao.loadGameInfos();
        parsePlayers();
        return !gameInfoBeans.isEmpty();
    }

    @Override
    public List<GameInfoBean> getGameInfoBeans() {
        tryToReloadData();
        return gameInfoBeans;
    }

    @Override
    public Map<String, PlayerDataBean> getPlayerDataBeans() {
        tryToReloadData();
        return playerDataBeans;
    }

    @Override
    public List<PlayerDataBean> getPlayerWithRanking(String type) {
        tryToReloadData();
        List<PlayerDataBean> result = new ArrayList<PlayerDataBean>(playerDataBeans.values());
        if (!StringUtils.isNotEmpty(type)) {
            throw new IllegalArgumentException(getSupportTypeDesription());
        }

        RankingType rankingType;
        try {
            rankingType = RankingType.valueOf(type);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(getSupportTypeDesription());
        }
        switch (rankingType) {
            case sum:
                Collections.sort(result, new PlayerDataBean.SumComparator());
                break;
            case count:
                Collections.sort(result, new PlayerDataBean.CountComparator());
                break;
            case mean:
                Collections.sort(result, new PlayerDataBean.MeanComparator());
                break;
            case stddev:
                Collections.sort(result, new PlayerDataBean.StdDevComparator());
                break;
            default:
                throw new IllegalArgumentException(getSupportTypeDesription());
        }
        Collections.reverse(result);
        return result;
    }

    private String getSupportTypeDesription() {
        StringBuilder content = new StringBuilder();
        for (RankingType rankingType : RankingType.values()) {
            content.append(rankingType);
            content.append(", ");
        }
        return content.toString();
    }

    private void tryToReloadData() {
        if (gameInfoBeans.isEmpty()) {
            reloadData();
        }
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
