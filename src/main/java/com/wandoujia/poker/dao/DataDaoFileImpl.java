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
import com.wandoujia.poker.models.SeasonDurationBean;
import com.wandoujia.poker.util.ContentParser;
import com.wandoujia.poker.util.DateUtil;

/**
 * @author chentian
 *         Read and write data from local file
 */
@Service
public class DataDaoFileImpl implements DataDao {

    private static final String COMMENTS_PREFIX = "#";

    private static final String PATH_DELIMITER = "/";

    private static final String SEASON_FILE_NAME = "season.season";

    private static final String[] DATA_FILE_EXTENSIONS = new String[]{"txt"};

    @Autowired
    @Qualifier("dataFileDir")
    private String dataFileDir;

    @Override
    public List<GameInfoBean> loadGameInfos(String seasonName, Map<String, SeasonDurationBean> seasonDurations) {
        AtomicReference<File> dataDirectory = new AtomicReference<>(new File(this.dataFileDir));
        if (!dataDirectory.get().exists()) {
            return Collections.emptyList();
        }

        List<GameInfoBean> result = new ArrayList<>();
        for (File file : FileUtils.listFiles(dataDirectory.get(), DATA_FILE_EXTENSIONS, false)) {
            if (!DateUtil.isDateInSeason(file.getName(), seasonName, seasonDurations)) {
                continue;
            }

            try {
                result.add(readGameInfoBeanFromFile(file));
            } catch (IOException | ParseException ignored) {
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
            writer = new PrintWriter(this.dataFileDir + PATH_DELIMITER + fileName, "UTF-8");
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

    @Override
    public Map<String, SeasonDurationBean> loadSeasonDurations() {
        Map<String, SeasonDurationBean> result = new HashMap<>();
        int seasonIndex = 1;
        File file = new File(getSeasonFileName());
        try {
            for (String line : FileUtils.readLines(file)) {
                SeasonDurationBean seasonDurationBean = ContentParser.getSeasonDuration(line);
                if (seasonDurationBean != null) {
                    result.put(String.valueOf(seasonIndex++), seasonDurationBean);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean updateSeasonDurations(List<SeasonDurationBean> seasonDurationBeans) {
        PrintWriter writer;
        try {
            writer = new PrintWriter(getSeasonFileName(), "UTF-8");
            for (SeasonDurationBean seasonDurationBean : seasonDurationBeans) {
                String startDate = DateUtil.DATE_FORMATTER.format(seasonDurationBean.getStartDate());
                String endDate = DateUtil.DATE_FORMATTER.format(seasonDurationBean.getEndDate());
                writer.println(startDate + "\t" + endDate);
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

    private String getSeasonFileName() {
        return this.dataFileDir + PATH_DELIMITER + SEASON_FILE_NAME;
    }

    private GameInfoBean readGameInfoBeanFromFile(File file) throws IOException, ParseException {
        Date date = DateUtil.DATE_FORMATTER.parse(file.getName());
        StringBuilder comments = new StringBuilder();
        List<Pair<String, Double>> players = new ArrayList<>();
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
