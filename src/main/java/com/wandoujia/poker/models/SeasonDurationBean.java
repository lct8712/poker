package com.wandoujia.poker.models;

import java.util.Date;

import lombok.Data;

/**
 * @author chentian
 */
@Data
public class SeasonDurationBean {

    private Date startDate;

    private Date endDate;

    public SeasonDurationBean(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "SeasonBean{" +
                "startDate=" + startDate +
                ", endDate=" + endDate + '}';
    }
}
