package com.wandoujia.poker.dao;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wandoujia.poker.models.GameInfoBean;
import com.wandoujia.poker.models.SeasonDurationBean;
import com.wandoujia.poker.util.DateUtil;

@ContextConfiguration(locations = { "classpath:applicationContext-poker.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class DataDaoFileImplTest {

    @Autowired
    private DataDao dataDao;

    @Test
    public void testLoadGameInfos() throws Exception {
        Map<String, SeasonDurationBean> seasonBeanMap = dataDao.loadSeasonDurations();
        List<GameInfoBean> gameInfos = dataDao.loadGameInfos("1", seasonBeanMap);
        Assert.assertEquals(68, gameInfos.size());

        for (GameInfoBean gameInfo : gameInfos) {
            if (!gameInfo.isValidate()) {
                System.out.println(DateUtil.DATE_FORMATTER.format(gameInfo.getDate()));
                System.out.println(gameInfo.computeSum());
            }
            Assert.assertTrue(gameInfo.isValidate());
        }

        gameInfos = dataDao.loadGameInfos("99", seasonBeanMap);
        Assert.assertEquals(0, gameInfos.size());
    }
}
