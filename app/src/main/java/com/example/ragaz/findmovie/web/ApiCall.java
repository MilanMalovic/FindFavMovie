package com.example.ragaz.findmovie.web;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiCall {


    public static OMDBApiService createRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIContract.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        OMDBApiService service = retrofit.create(OMDBApiService.class);
        return service;
    }
}