package com.muei.apm.taxi5.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApiObject {
    @SerializedName("firstname")
    @Expose
    public String firstName;

    @SerializedName("lastname")
    @Expose
    public String lastName;

    @SerializedName("email")
    @Expose
    public String email;

    @SerializedName("psswd")
    @Expose
    public String psswd;

    @SerializedName("phone")
    @Expose
    public String phone;

    public ApiObject(String firstName, String lastName, String email, String phone, String psswd) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.psswd = psswd;
    }

    @Override
    public String toString() {
        return "{" +
                "firstname='" + firstName + '\'' +
                ", lastname='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", psswd='" + psswd + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
