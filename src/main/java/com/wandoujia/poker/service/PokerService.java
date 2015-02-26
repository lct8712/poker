package com.wandoujia.poker.service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import com.wandoujia.poker.models.ApiResult;
import com.wandoujia.poker.models.GameInfoBean;
import com.wandoujia.poker.models.PlayerDataBean;
import com.wandoujia.poker.models.SeasonDurationBean;

/**
 * @author chentian
 */
public interface PokerService {

    boolean reloadData();

    List<GameInfoBean> getGameInfoBeans(String seasonName);

    GameInfoBean getGameInfoBean(String date);

    ApiResult updateGame(String date, String content, String comment);

    Map<String, PlayerDataBean> getPlayerDataBeans(String seasonName);

    List<PlayerDataBean> getPlayerWithRanking(String type, String seasonName);

    Map<String, SeasonDurationBean> getSeasonDurationBeans();

    ApiResult updateSeasonDuration(String content);

    void compressAllData(OutputStream out) throws IOException;
}
