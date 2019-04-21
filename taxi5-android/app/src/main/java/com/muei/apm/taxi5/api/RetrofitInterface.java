package com.muei.apm.taxi5.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitInterface {

    @POST("/user")
    Call<ApiObject> createUser(@Body ApiObject user);

}
