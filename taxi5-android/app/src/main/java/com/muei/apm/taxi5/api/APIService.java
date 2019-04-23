package com.muei.apm.taxi5.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIService {

    @POST("/user")
    Call<ApiObject> createUser(@Body ApiObject user);

    @POST("/login")
    Call<LoginObject> loginUser(@Body LoginObject user);

    @GET("/currentuser")
    Call<ApiObject> getCurrentUserId();

    @GET("/user/{id}")
    Call<ApiObject> getUserDetails(@Path("id") long id);

}
