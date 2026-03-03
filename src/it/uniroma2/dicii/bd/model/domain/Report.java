package it.uniroma2.dicii.bd.model.domain;

import java.sql.Date;

public class Report {
    User user;
    Date date;
    float percentage;
    public Report(User user, Date date, float percentage) {
        this.user = user;
        this.date = date;
        this.percentage = percentage;
    }

    public User getUser() {
        return user;
    }

    public Date getDate() {
        return date;
    }

    public float getPercentage() {
        return percentage;
    }
}
