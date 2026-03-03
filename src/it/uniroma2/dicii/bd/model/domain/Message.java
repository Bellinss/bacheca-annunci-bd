package it.uniroma2.dicii.bd.model.domain;

import java.sql.Time;
import java.util.Date;

public class Message {
    private int id;
    private Date date;
    private Time hour;
    private String text;
    private User owner;
    public Message(User owner, Date date, Time hour, String text) {
        this.owner = owner;
        this.date = date;
        this.hour = hour;
        this.text = text;
    }

    public Message(String text) {
        this.text = text;
    }

    public User getOwner() {
        return owner;
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
}
