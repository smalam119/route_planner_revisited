package com.thyme.smalam119.routeplannerapplication.NetworkCalls;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by smalam119 on 12/19/17.
 */

public class RetroFitClient {

    public static final String BASE_URL = "https://maps.googleapis.com/maps/";
    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
