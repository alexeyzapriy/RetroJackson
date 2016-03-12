package com.example.azapriy.retrojackson;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {
    @GET("current")
    Call<Weather> currentCity(@Query("city") String city);
}
