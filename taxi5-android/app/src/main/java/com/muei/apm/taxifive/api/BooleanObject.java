package com.muei.apm.taxifive.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BooleanObject {

    @SerializedName("success")
    @Expose
    public boolean success;


    public BooleanObject(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "{" +
                "success='" + success + '\'' +
                '}';
    }

}


