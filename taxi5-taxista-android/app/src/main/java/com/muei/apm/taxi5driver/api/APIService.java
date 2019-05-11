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

    @PUT("/sendcost/{id}")
    Call<RideObject> sendRideCost(@Path("id") long id, @Body RideCostObject cost);

    @GET("/activeride")
    Call<List<RideObject>> getRides();

    @GET("/ride/{id}")
    Call<RideObject> getRideById(@Path("id") long id);

    @GET("/user/{id}")
    Call<ApiObject> getUserDetails(@Path("id") long id);

}