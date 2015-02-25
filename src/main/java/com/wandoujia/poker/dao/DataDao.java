package com.wandoujia.poker.dao;

import java.util.List;
import java.util.Map;

import com.wandoujia.poker.models.GameInfoBean;
import com.wandoujia.poker.models.SeasonDurationBean;

/**
 * @author chentian
 * Get data from file
 */
public interface DataDao {

    List<GameInfoBean> loadGameInfos(String seasonNumber, Map<String, SeasonDurationBean> seasonDurations);

    boolean updateGameInfo(GameInfoBean gameInfoBean);

    Map<String, SeasonDurationBean> loadSeasonDurations();

    boolean updateSeasonDurations(List<SeasonDurationBean> seasonDurationBeans);
}
