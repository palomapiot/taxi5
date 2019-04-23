package com.muei.apm.taxi5.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginObject {

    @SerializedName("id")
    @Expose
    public Long id;

    @SerializedName("email")
    @Expose
    public String email;

    @SerializedName("psswd")
    @Expose
    public String psswd;

    public LoginObject(String email, String psswd) {
        this.email = email;
        this.psswd = psswd;
    }

    @Override
    public String toString() {
        return "{" +
                ", id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", psswd='" + psswd + '\'' +
                '}';
    }

}
