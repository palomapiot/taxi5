package com.muei.apm.taxifive.ui;

public class Route {

    private String date;
    private String origin;
    private String destination;

    public Route() {
        super();
    }

    public Route(String date, String origin, String destination) {
        super();
        this.date = date;
        this.origin = origin;
        this.destination = destination;
    }

    public String getDate() {
        return date;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

}
