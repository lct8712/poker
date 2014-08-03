package com.wandoujia.poker.dao;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import com.wandoujia.poker.models.GameInfoBean;

import static org.junit.Assert.assertEquals;

@ContextConfiguration("file:src/main/resources/mvc-dispatcher-servlet.xml")
public class DataDaoFileImplTest {
    private static final String DATA_FILE_DIR = "/Users/lingchentian/Documents/Github/poker/src/test/resources/data";
    private DataDaoFileImpl fileDao = new DataDaoFileImpl();

    @Before
    public void init() {
        fileDao.setDataFileDir(DATA_FILE_DIR);
    }

    @Test
    public void testLoadGameInfos() throws Exception {
        List<GameInfoBean> gameInfos = fileDao.loadGameInfos();
        assertEquals(46, gameInfos.size());
    }
}
