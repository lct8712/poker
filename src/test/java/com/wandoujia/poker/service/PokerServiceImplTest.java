package com.wandoujia.poker.service;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.wandoujia.poker.dao.DataDaoFileImpl;
import com.wandoujia.poker.models.GameInfoBean;
import com.wandoujia.poker.models.PlayerDataBean;

public class PokerServiceImplTest {
    private static final String DATA_FILE_DIR = "/Users/lingchentian/Documents/Github/poker/src/test/resources/data";
    private DataDaoFileImpl fileDao = new DataDaoFileImpl();
    private PokerServiceImpl pokerService = new PokerServiceImpl();

    @Before
    public void init() {
        fileDao.setDataFileDir(DATA_FILE_DIR);
        pokerService.setDataDao(fileDao);
        pokerService.reloadData();
    }

    @Test
    public void testGetGameInfoBeans() throws Exception {
        List<GameInfoBean> games = pokerService.getGameInfoBeans();
        Map<String, PlayerDataBean> players = pokerService.getPlayerDataBeans();

        for (PlayerDataBean player : players.values()) {
            System.out.printf("%s\t%.2f\t%.2f\t%.2f\t%d\n",
                    player.getName(), player.getSum(), player.getMean(),
                    player.getStdDev(), player.getHistory().size());
        }
    }

    @Test
    public void testGetPlayerDataBeans() throws Exception {

    }
}
