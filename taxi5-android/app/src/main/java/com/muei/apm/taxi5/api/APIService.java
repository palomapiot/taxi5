package com.muei.apm.taxi5.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface APIService {

    @POST("/user")
    Call<ApiObject> createUser(@Body ApiObject user);

    @POST("/login")
    Call<ApiObject> loginUser(@Body LoginObject user);

}
