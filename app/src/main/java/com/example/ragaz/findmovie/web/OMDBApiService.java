package com.example.ragaz.findmovie.web;

import com.example.ragaz.findmovie.model.Response;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface OMDBApiService {

    //BASE URL                  //Putanja doServis  //Parametri
    //TODO: http://www.omdbapi.com        /             ?s=Batman&plot=full&apikey=3ec74ff6
    @GET("/")
    Call<Response> searchODMApi(@QueryMap Map<String, String> options);

    @GET("/")
    Call<com.example.ragaz.findmovie.model.Movie> getInfoDataByID(@QueryMap Map<String, String> options);
}
