package com.muei.apm.taxi5.api;

public class ApiUtils {

    private ApiUtils() {}

    public static final String BASE_URL = "https://taxi5-server.herokuapp.com/";

    public static APIService getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}