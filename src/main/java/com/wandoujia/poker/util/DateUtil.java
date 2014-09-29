package com.wandoujia.poker.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.google.gdata.util.common.base.Pair;

/**
 * @author chentian
 */
public class DateUtil {

    public static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyyMMdd");

    private static final Map<String, Pair<Date, Date>> seasons = new HashMap<>();

    static {
        try {
            seasons.put("1", new Pair<>(DATE_FORMATTER.parse("20010101"), DATE_FORMATTER.parse("20140928")));
            seasons.put("2", new Pair<>(DATE_FORMATTER.parse("20140929"), DATE_FORMATTER.parse("21001231")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getSeasonList() {
        return Arrays.asList(seasons.keySet().toArray(new String[seasons.size()]));
    }

    public static boolean isDateInSeason(String dateStr, String season) {
        Pair<Date, Date> duration = seasons.get(season);
        if (duration == null) {
            return  false;
        }

        try {
            Date date = DATE_FORMATTER.parse(dateStr);
            return date.after(duration.getFirst()) && date.before(duration.getSecond());
        } catch (ParseException e) {
            return false;
        }
    }
}
