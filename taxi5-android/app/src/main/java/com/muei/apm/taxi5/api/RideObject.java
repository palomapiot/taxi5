package com.muei.apm.taxi5.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RideObject {

    @SerializedName("origin")
    @Expose
    public String origin;

    @SerializedName("destination")
    @Expose
    public String destination;

    @SerializedName("ridedate")
    @Expose
    public String ridedate;

    @SerializedName("price")
    @Expose
    public float price;

    @SerializedName("userid")
    @Expose
    public Long userid;

    public RideObject(String origin, String destination, String ridedate, float price, Long userid) {
        this.origin = origin;
        this.destination = destination;
        this.ridedate = ridedate;
        this.price = price;
        this.userid = userid;
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
