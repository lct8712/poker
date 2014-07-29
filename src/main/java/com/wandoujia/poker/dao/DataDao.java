package com.wandoujia.poker.dao;

import java.util.List;

import com.wandoujia.poker.models.GameInfoBean;

/**
 * @author chentian
 * Get data from file
 */
public interface DataDao {
    List<GameInfoBean> loadGameInfos();
}
