package com.muei.apm.taxi5.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdatePsswdObject {


    @SerializedName("psswd")
    @Expose
    public String psswd;

    public UpdatePsswdObject(String psswd) {
        this.psswd = psswd;
    }

    @Override
    public String toString() {
        return "{" +
                ", psswd='" + psswd + '\'' +
                '}';
    }
}
