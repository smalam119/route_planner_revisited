package com.thyme.smalam119.routeplannerapplication.NetworkCalls;

import com.thyme.smalam119.routeplannerapplication.Model.Direction.Example;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by smalam119 on 12/19/17.
 */

public interface ApiInterface {

    @GET("api/directions/json")
    Call<Example> getDistanceDuration(@Query("units") String units, @Query("origin") String origin, @Query("destination") String destination, @Query("mode") String mode);
}
