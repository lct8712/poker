package com.wandoujia.poker.service;

import java.util.List;
import java.util.Map;

import com.wandoujia.poker.models.GameInfoBean;
import com.wandoujia.poker.models.PlayerDataBean;

/**
 * @author chentian
 */
public interface PokerService {
    boolean reloadData();
    List<GameInfoBean> getGameInfoBeans();
    Map<String, PlayerDataBean> getPlayerDataBeans();
}
