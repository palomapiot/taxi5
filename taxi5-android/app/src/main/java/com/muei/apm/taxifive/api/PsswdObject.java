package com.muei.apm.taxifive.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PsswdObject {

    @SerializedName("psswd")
    @Expose
    public String psswd;


    public PsswdObject(String psswd) {
        this.psswd = psswd;
    }

    @Override
    public String toString() {
        return "{" +
                "psswd='" + psswd + '\'' +
                '}';
    }
}
