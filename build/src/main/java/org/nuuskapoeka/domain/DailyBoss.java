package org.nuuskapoeka.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class DailyBoss {

    private List<LocalDate> date;
    private String name;
    private String buildUrl;
    private String bossUrlId;

    public DailyBoss(LocalDate date, String name, String buildUrl, String bossUrlId){
        this.date = new ArrayList<>();
        this.date.add(date);
        this.name = name;
        this.buildUrl = buildUrl;
        this.bossUrlId = bossUrlId;
    }

    public List<LocalDate> getDates() {
        return date;
    }

    public void setDate(List<LocalDate> date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBuildUrl() {
        return buildUrl;
    }

    public void setBuildUrl(String buildUrl) {
        this.buildUrl = buildUrl;
    }

    public String getBossUrlId() {
        return bossUrlId;
    }

    public void setBossUrlId(String bossUrlId) {
        this.bossUrlId = bossUrlId;
    }
}
