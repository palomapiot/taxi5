package com.muei.apm.taxi5.api;

public class ApiUtils {

    private ApiUtils() {}

    public static final String BASE_URL = "http://192.168.43.208:5000/";

    public static APIService getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}