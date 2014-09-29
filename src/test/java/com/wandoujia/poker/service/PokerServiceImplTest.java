package com.wandoujia.poker.service;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wandoujia.poker.models.GameInfoBean;
import com.wandoujia.poker.models.PlayerDataBean;

import static org.junit.Assert.assertFalse;

@ContextConfiguration(locations = { "classpath:applicationContext-poker.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class PokerServiceImplTest {

    @Autowired
    private PokerServiceImpl pokerService;

    @Before
    public void init() {
        pokerService.reloadData();
    }

    @Test
    public void testGetGameInfoBeans() throws Exception {
        List<GameInfoBean> games = pokerService.getGameInfoBeans("1");
        assertFalse(games.isEmpty());
        Map<String, PlayerDataBean> players = pokerService.getPlayerDataBeans("1");
        assertFalse(players.isEmpty());

        for (PlayerDataBean player : players.values()) {
            System.out.printf("%s\t%.2f\t%.2f\t%.2f\t%d\n",
                    player.getName(), player.getSum(), player.getMean(),
                    player.getStdDev(), player.getHistoryMoney().size());
        }
    }

    @Test
    public void testGetPlayerDataBeans() throws Exception {

    }
}
