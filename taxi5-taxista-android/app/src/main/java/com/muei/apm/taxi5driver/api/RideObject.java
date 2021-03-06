package com.muei.apm.taxi5driver.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RideObject {

    @SerializedName("id")
    @Expose
    public Long id;

    @SerializedName("origin")
    @Expose
    public String origin;

    @SerializedName("destination")
    @Expose
    public String destination;

    @SerializedName("ridedate")
    @Expose
    public long ridedate;

    @SerializedName("price")
    @Expose
    public float price;

    @SerializedName("userid")
    @Expose
    public Long userid;

    @SerializedName("taxiid")
    @Expose
    public Long taxiid;

    @SerializedName("cost")
    @Expose
    public float cost;

    public RideObject(String origin, String destination, long ridedate, float price, Long userid) {
        this.origin = origin;
        this.destination = destination;
        this.ridedate = ridedate;
        this.price = price;
        this.userid = userid;
    }

    public RideObject(String origin, String destination, long ridedate, Long userid) {
        this.origin = origin;
        this.destination = destination;
        this.ridedate = ridedate;
        this.userid = userid;
    }

    public RideObject(String origin, String destination, long ridedate, float price, Long userid, Long taxiid, float cost) {
        this.origin = origin;
        this.destination = destination;
        this.ridedate = ridedate;
        this.price = price;
        this.userid = userid;
        this.taxiid = taxiid;
        this.cost = cost;
    }

    public RideObject(float price) {
        this.price = price;
    }


    @Override
    public String toString() {
        return "{" +
                "origin='" + origin + '\'' +
                ", destination='" + destination + '\'' +
                ", ridedate='" + ridedate + '\'' +
                ", price=" + price +
                ", userid=" + userid +
                '}';
    }
}