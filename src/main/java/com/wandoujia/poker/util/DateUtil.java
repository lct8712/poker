package com.wandoujia.poker.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.wandoujia.poker.models.SeasonDurationBean;

/**
 * @author chentian
 */
public class DateUtil {

    public static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyyMMdd");

    public static boolean isDateInSeason(String dateStr, String seasonName, Map<String, SeasonDurationBean> seasonDurations) {
        SeasonDurationBean duration = seasonDurations.get(seasonName);
        if (duration == null) {
            return  false;
        }

        try {
            Date date = DATE_FORMATTER.parse(dateStr);
            return date.after(duration.getStartDate()) && date.before(duration.getEndDate());
        } catch (ParseException e) {
            return false;
        }
    }
}
