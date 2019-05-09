package com.muei.apm.taxi5driver.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TaxiIdLogin {

    @SerializedName("taxiid")
    @Expose
    public Long taxiid;


    public TaxiIdLogin(Long taxiid) {
        this.taxiid = taxiid;
    }

    @Override
    public String toString() {
        return "{" +
                ", taxiid='" + taxiid + '\'' +
                '}';
    }
}
