package com.example.bootcampandroid;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientApi {

    public static String URL = "https://api.themoviedb.org/3/";
    public static Retrofit retrofit = null;

    //method for communicating with and fetching data from url to our app
    public Retrofit getJSON(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }
}
