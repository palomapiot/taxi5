package com.muei.apm.taxi5driver.model;

import java.util.Calendar;

public class Travel {

    private String origin;

    private String destination;

    private String user;

    private Calendar date;

    public Travel(String origin, String destination, String user, Calendar date) {
        this.origin = origin;
        this.destination = destination;
        this.user = user;
        this.date = date;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }
}