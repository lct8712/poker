package com.wandoujia.poker.service;

import java.util.List;
import java.util.Map;

import com.wandoujia.poker.models.ApiResult;
import com.wandoujia.poker.models.GameInfoBean;
import com.wandoujia.poker.models.PlayerDataBean;

/**
 * @author chentian
 */
public interface PokerService {

    boolean reloadData();

    List<GameInfoBean> getGameInfoBeans();

    GameInfoBean getGameInfoBean(String date);

    ApiResult updateGame(String date, String content, String comment);

    Map<String, PlayerDataBean> getPlayerDataBeans();

    List<PlayerDataBean> getPlayerWithRanking(String type);
}
