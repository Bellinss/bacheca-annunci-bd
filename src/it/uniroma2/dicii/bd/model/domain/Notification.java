package it.uniroma2.dicii.bd.model.domain;

import java.sql.Date;

public class Notification {
    int id;
    String text;
    Date date;
    public Notification(int id, Date date, String text) {
        this.id = id;
        this.text = text;
        this.date = date;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
