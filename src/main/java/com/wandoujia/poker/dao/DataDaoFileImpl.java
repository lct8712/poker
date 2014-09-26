package com.wandoujia.poker.dao;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Repository;

import com.sun.tools.javac.util.Pair;
import com.wandoujia.poker.models.GameInfoBean;

/**
 * @author chentian
 * Read data from local file
 */
@Repository
public class DataDaoFileImpl implements DataDao {
    private static final String COMMENTS_PREFIX = "#";
    private static final Pattern PLAYER_PATTERN = Pattern.compile("\\s+");
    private static final String[] DATA_FILE_EXTENSIONS = new String[]{"txt"};

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

    private GameInfoBean readGameInfoBeanFromFile(File file) throws IOException, ParseException {
        Date date = (new SimpleDateFormat("yyyyMMdd")).parse(file.getName());
        StringBuilder comments = new StringBuilder();
        List<Pair<String, Double>> players = new ArrayList<Pair<String, Double>>();
        for (String line : FileUtils.readLines(file)) {
            if (line.startsWith(COMMENTS_PREFIX)) {
                comments.append(line);
            } else {
                Pair<String, Double> playerInfo = getPlayerInfo(line);
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
                return p2.snd.compareTo(p1.snd);
            }
        });

        GameInfoBean result = new GameInfoBean();
        result.setComments((comments.toString().trim()));
        result.setPlayers(players);
        result.setDate(date);
        return result;
    }

    private Pair<String, Double> getPlayerInfo(String line) {
        String[] items = PLAYER_PATTERN.split(line);
        if (items.length != 2) {
            return null;
        }
        try {
            return new Pair<String, Double>(items[0].trim(), Double.valueOf(items[1]));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public void setDataFileDir(String dataFileDir) {
        this.dataFileDir = dataFileDir;
    }
}
