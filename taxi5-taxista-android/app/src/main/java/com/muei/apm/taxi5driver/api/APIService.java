package com.muei.apm.taxi5driver.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface APIService {

    @POST("/taxilogin")
    Call<LoginObject> loginTaxi(@Body LoginObject login);

    @GET("/taxirides/{id}")
    Call<List<RideObject>> getTaxiRides(@Path("id") long id);

    @PUT("/taxiride/{id}")
    Call<RideObject> asignRide(@Path("id") long id, @Body TaxiIdLogin taxiid);

    @GET("/activeride")
    Call<List<RideObject>> getRides();



}