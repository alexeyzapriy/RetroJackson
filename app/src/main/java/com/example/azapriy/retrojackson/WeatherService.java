package com.example.azapriy.retrojackson;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface WeatherService {
    @GET("current")
    Observable<Weather> currentCity(@Query("city") String city);
}
