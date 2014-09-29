package com.wandoujia.poker.dao;

import java.io.*;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.google.gdata.util.common.base.Pair;
import com.wandoujia.poker.models.GameInfoBean;
import com.wandoujia.poker.util.ContentParser;
import com.wandoujia.poker.util.DateUtil;

/**
 * @author chentian
 *         Read data from local file
 */
@Service
public class DataDaoFileImpl implements DataDao {

    private static final String COMMENTS_PREFIX = "#";

    private static final String[] DATA_FILE_EXTENSIONS = new String[]{"txt"};

    @Autowired
    @Qualifier("dataFileDir")
    private String dataFileDir;

    @Override
    public List<GameInfoBean> loadGameInfos() {

        AtomicReference<File> dataDirectory = new AtomicReference<File>(new File(this.dataFileDir));
        if (!dataDirectory.get().exists()) {
            return Collections.emptyList();
        }

        List<GameInfoBean> result = new ArrayList<GameInfoBean>();
        for (File file : FileUtils.listFiles(dataDirectory.get(), DATA_FILE_EXTENSIONS, false)) {
            try {
                result.add(readGameInfoBeanFromFile(file));
            } catch (IOException ignored) {
            } catch (ParseException ignored) {
            }
        }
        Collections.sort(result, new GameInfoBean.DescComparator());
        return result;
    }

    @Override
    public boolean updateGameInfo(GameInfoBean gameInfoBean) {
        String fileName = DateUtil.DATE_FORMATTER.format(gameInfoBean.getDate()) + ".txt";
        PrintWriter writer;
        try {
            writer = new PrintWriter(this.dataFileDir + "/" + fileName, "UTF-8");
            if (!gameInfoBean.getComments().isEmpty()) {
                writer.println("# " + gameInfoBean.getComments());
            }
            for (Pair<String, Double> player : gameInfoBean.getPlayers()) {
                writer.println(player.getFirst() + "\t" + player.getSecond());
            }
            writer.close();
        } catch (FileNotFoundException e) {
            return false;
        } catch (UnsupportedEncodingException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
        return true;
    }

    private GameInfoBean readGameInfoBeanFromFile(File file) throws IOException, ParseException {
        Date date = DateUtil.DATE_FORMATTER.parse(file.getName());
        StringBuilder comments = new StringBuilder();
        List<Pair<String, Double>> players = new ArrayList<Pair<String, Double>>();
        for (String line : FileUtils.readLines(file)) {
            if (line.startsWith(COMMENTS_PREFIX)) {
                comments.append(line);
            } else {
                Pair<String, Double> playerInfo = ContentParser.getPlayerInfo(line);
                if (playerInfo != null) {
                    players.add(playerInfo);
                } else {
                    System.out.println("Load game info failed: " + file.getName());
                }
            }
        }
        Collections.sort(players, new Comparator<Pair<String, Double>>() {
            @Override
            public int compare(Pair<String, Double> p1, Pair<String, Double> p2) {
                return p2.getSecond().compareTo(p1.getSecond());
            }
        });

        GameInfoBean result = new GameInfoBean();
        result.setComments((comments.toString().trim()));
        result.setPlayers(players);
        result.setDate(date);
        return result;
    }

}
