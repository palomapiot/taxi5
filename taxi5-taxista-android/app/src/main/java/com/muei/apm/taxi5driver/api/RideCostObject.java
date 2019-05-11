package com.muei.apm.taxi5driver.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RideCostObject {

    @SerializedName("cost")
    @Expose
    public float cost;


    public RideCostObject(float cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "{" +
                ", cost='" + cost + '\'' +
                '}';
    }
}
