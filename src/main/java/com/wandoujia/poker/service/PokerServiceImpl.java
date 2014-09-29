package com.wandoujia.poker.service;

import com.google.gdata.util.common.base.Pair;
import com.wandoujia.poker.dao.DataDao;
import com.wandoujia.poker.models.ApiResult;
import com.wandoujia.poker.models.GameInfoBean;
import com.wandoujia.poker.models.PlayerDataBean;
import com.wandoujia.poker.util.ContentParser;
import com.wandoujia.poker.util.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.*;

/**
 * @author chentian
 */
@Service
public class PokerServiceImpl implements PokerService {

    @Autowired
    private DataDao dataDao;

    private List<GameInfoBean> gameInfoBeans;

    private Map<String, PlayerDataBean> playerDataBeans;

    public static final String LINE_DELIMITER = "\n";

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
    public GameInfoBean getGameInfoBean(String dateStr) {
        try {
            Date date = DateUtil.DATE_FORMATTER.parse(dateStr);
            for (GameInfoBean gameInfoBean : gameInfoBeans) {
                if (gameInfoBean.getDate().equals(date)) {
                    return gameInfoBean;
                }
            }
            return null;
        } catch (ParseException e) {
            return null;
        }
    }

    @Override
    public ApiResult updateGame(String dateStr, String content, String comment) {
        try {
            List<Pair<String, Double>> players = new ArrayList<Pair<String, Double>>();
            String[] lines = content.split(LINE_DELIMITER);
            for (String line : lines) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                Pair<String, Double> playerInfo = ContentParser.getPlayerInfo(line);
                if (playerInfo != null) {
                    players.add(playerInfo);
                } else {
                    return new ApiResult(false, "Line format error: " + line);
                }
            }

            GameInfoBean gameInfoBean = new GameInfoBean();
            gameInfoBean.setDate(DateUtil.DATE_FORMATTER.parse(dateStr));
            gameInfoBean.setComments(comment);
            gameInfoBean.setPlayers(players);

            if (!gameInfoBean.isValidate()) {
                return new ApiResult(false, "Sum of money is bigger than 10.");
            }
            if (!dataDao.updateGameInfo(gameInfoBean)) {
                return new ApiResult(false, "Update error.");
            }
            reloadData();
            return new ApiResult(true);
        } catch (ParseException e) {
            return new ApiResult(false, "Date format error, should be: yyyyMMdd");
        }
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
        if (gameInfoBeans == null || gameInfoBeans.isEmpty()) {
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
