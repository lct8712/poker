package com.wandoujia.poker.service;

import java.text.ParseException;
import java.util.*;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gdata.util.common.base.Pair;
import com.wandoujia.poker.dao.DataDao;
import com.wandoujia.poker.models.ApiResult;
import com.wandoujia.poker.models.GameInfoBean;
import com.wandoujia.poker.models.PlayerDataBean;
import com.wandoujia.poker.models.SeasonInfoBean;
import com.wandoujia.poker.util.ContentParser;
import com.wandoujia.poker.util.DateUtil;

/**
 * @author chentian
 */
@Service
public class PokerServiceImpl implements PokerService {

    @Autowired
    private DataDao dataDao;

    private Map<String, SeasonInfoBean> seasonInfoBeanMap;

    public static final String LINE_DELIMITER = "\n";

    enum RankingType {
        sum, count, mean, stddev
    }

    @Override
    public boolean reloadData() {
        seasonInfoBeanMap = new HashMap<>();
        boolean result = false;
        for (String season : DateUtil.getSeasonList()) {
            result |= reloadData(season);
        }
        return result;
    }

    @Override
    public List<GameInfoBean> getGameInfoBeans(String seasonName) {
        tryToReloadData();
        return getOrCreateSeason(seasonName).getGameInfoBeans();
    }

    @Override
    public GameInfoBean getGameInfoBean(String dateStr) {
        try {
            Date date = DateUtil.DATE_FORMATTER.parse(dateStr);
            for (SeasonInfoBean seasonInfoBean : seasonInfoBeanMap.values()) {
                for (GameInfoBean gameInfoBean : seasonInfoBean.getGameInfoBeans()) {
                    if (gameInfoBean.getDate().equals(date)) {
                        return gameInfoBean;
                    }
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
            List<Pair<String, Double>> players = new ArrayList<>();
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
    public Map<String, PlayerDataBean> getPlayerDataBeans(String seasonName) {
        tryToReloadData();
        return getOrCreateSeason(seasonName).getPlayerDataBeans();
    }

    @Override
    public List<PlayerDataBean> getPlayerWithRanking(String type, String seasonName) {
        tryToReloadData();
        List<PlayerDataBean> result = new ArrayList<>(getPlayerDataBeans(seasonName).values());
        if (!StringUtils.isNotEmpty(type)) {
            throw new IllegalArgumentException(getSupportTypeDescription());
        }

        RankingType rankingType;
        try {
            rankingType = RankingType.valueOf(type);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(getSupportTypeDescription());
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
                throw new IllegalArgumentException(getSupportTypeDescription());
        }
        Collections.reverse(result);
        return result;
    }

    private boolean reloadData(String seasonName) {
        SeasonInfoBean currentSeason = getOrCreateSeason(seasonName);
        currentSeason.setGameInfoBeans(dataDao.loadGameInfos(seasonName));
        currentSeason.parsePlayers();
        return !currentSeason.getGameInfoBeans().isEmpty();
    }

    private void tryToReloadData() {
        if (seasonInfoBeanMap == null || seasonInfoBeanMap.isEmpty()) {
            reloadData();
        }
    }

    private String getSupportTypeDescription() {
        StringBuilder content = new StringBuilder();
        for (RankingType rankingType : RankingType.values()) {
            content.append(rankingType);
            content.append(", ");
        }
        return content.toString();
    }

    private SeasonInfoBean getOrCreateSeason(String seasonNumber) {
        if (seasonInfoBeanMap.containsKey(seasonNumber)) {
            return seasonInfoBeanMap.get(seasonNumber);
        }

        SeasonInfoBean currentSeason = new SeasonInfoBean(seasonNumber);
        seasonInfoBeanMap.put(seasonNumber, currentSeason);
        return currentSeason;
    }
}
