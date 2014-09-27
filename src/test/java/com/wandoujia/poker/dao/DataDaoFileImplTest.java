package com.wandoujia.poker.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wandoujia.poker.models.GameInfoBean;
import com.wandoujia.poker.util.DateUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/test/resources/mvc-dispatcher-servlet.xml")
public class DataDaoFileImplTest {

    @Autowired
    private DataDao dataDao;

    @Test
    public void testLoadGameInfos() throws Exception {
        List<GameInfoBean> gameInfos = dataDao.loadGameInfos();
        Assert.assertEquals(67, gameInfos.size());

        for (GameInfoBean gameInfo : gameInfos) {
            if (!gameInfo.isValidate()) {
                System.out.println(DateUtil.DATE_FORMATTER.format(gameInfo.getDate()));
                System.out.println(gameInfo.computeSum());
            }
//            Assert.assertTrue(gameInfo.isValidate());
        }
    }
}
