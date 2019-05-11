package com.muei.apm.taxifive.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface APIService {

    @POST("/user")
    Call<ApiObject> createUser(@Body ApiObject user);

    @POST("/login")
    Call<LoginObject> loginUser(@Body LoginObject user);

    @GET("/user/{id}")
    Call<ApiObject> getUserDetails(@Path("id") long id);

    @GET("/rides/{id}")
    Call<List<RideObject>> getUserRides(@Path("id") long id);

    @PUT("/user/{id}")
    Call<ApiObject> updateUserDetails(@Path("id") long id, @Body ApiObject user);

    @PUT("/userpsswd/{id}")
    Call<ApiObject> updatePsswd(@Path("id") long id, @Body UpdatePsswdObject user);

    @POST("/checkpsswd/{id}")
    Call<BooleanObject> checkPsswd(@Path("id") long id, @Body PsswdObject psswd);

    @POST("/ride")
    Call<RideObject> addRide(@Body RideObject ride);

    @PUT("/ride/{id}")
    Call<RideObject> updateRide(@Path("id") long id, @Body RideObject ride);

    @GET("/ride/{id}")
    Call<RideObject> getRideById(@Path("id") long id);

}
