package com.muei.apm.taxi5.api;

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

    @SerializedName("cost")
    @Expose
    public float cost;

    @SerializedName("userid")
    @Expose
    public Long userid;

    public RideObject(String origin, String destination, long ridedate, float price, Long userid) {
        this.origin = origin;
        this.destination = destination;
        this.ridedate = ridedate;
        this.price = price;
        this.userid = userid;
    }

    public RideObject(String origin, String destination, long ridedate, float price, Long userid, float cost) {
        this.origin = origin;
        this.destination = destination;
        this.ridedate = ridedate;
        this.price = price;
        this.userid = userid;
        this.cost = cost;
    }

    public RideObject(String origin, String destination, long ridedate, Long userid) {
        this.origin = origin;
        this.destination = destination;
        this.ridedate = ridedate;
        this.userid = userid;
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
