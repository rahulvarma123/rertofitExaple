package com.example.rertofitexaple;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String BASE_URL = "https://jaladhi-server.herokuapp.com";

    private static Retrofit retrofit;

    private RetrofitClient() {

    }

    public static Retrofit getInstance() {
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        return retrofit;
    }
}

