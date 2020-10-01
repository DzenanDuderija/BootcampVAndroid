package com.example.bootcampandroid;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface JsonPlaceHolderApi {

    //String baseURL = "https://api.themoviedb.org/3/search/";
    @GET("search/movie")
    //Call<MovieJson> getSearched(@Query("api_key") String apiKey);
    //Call<MovieJson> getSearched(@Query("name" String name);//it's not name!!!!!!!!
    Call<MoviesJson> getSearched(@Query("api_key") String API
                                 ,@Query("query") String name);
}
