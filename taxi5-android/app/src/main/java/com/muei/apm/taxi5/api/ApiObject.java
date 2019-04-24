package com.muei.apm.taxi5.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApiObject {
    @SerializedName("id")
    @Expose
    public Long id;

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

    public ApiObject(Long id, String firstName, String lastName, String email, String phone, String psswd) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.psswd = psswd;
    }

    public ApiObject(String firstName, String lastName, String email, String phone, String psswd) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.psswd = psswd;
    }

    public ApiObject(String firstName, String lastName, String email, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "{" +
                "id='" + id + '\'' +
                "firstname='" + firstName + '\'' +
                ", lastname='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", psswd='" + psswd + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
