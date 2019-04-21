package com.muei.apm.taxi5.api;

import com.google.gson.annotations.SerializedName;

public class ApiObject {
    @SerializedName("firstName")
    public String firstName;

    @SerializedName("lastName")
    public String lastName;

    @SerializedName("email")
    public String email;

    @SerializedName("psswd")
    public String psswd;

    @SerializedName("phone")
    public String phone;

    public ApiObject(String firstName, String lastName, String email, String phone, String psswd) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.psswd = psswd;
    }

}
