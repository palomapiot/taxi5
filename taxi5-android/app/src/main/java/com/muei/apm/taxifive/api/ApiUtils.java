package com.muei.apm.taxifive.api;

public class ApiUtils {

    private ApiUtils() {}

    public static final String BASE_URL = "https://powerful-coast-82606.herokuapp.com/";

    public static APIService getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}