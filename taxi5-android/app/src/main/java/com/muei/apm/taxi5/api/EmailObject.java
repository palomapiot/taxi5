package com.muei.apm.taxi5.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EmailObject {
    @Expose
    public Long id;

    @SerializedName("email")
    @Expose
    public String email;


    public EmailObject(String email, String psswd) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "{" +
                ", id='" + id + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
