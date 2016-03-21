package com.example.azapriy.retrojackson;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class MainActivity extends AppCompatActivity {
    public static final String WEATHER_SERVICE_URL = "https://agile-plains-64462.herokuapp.com/current?city=%s";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient();
        OkHttpClient httpClientWLogging = new OkHttpClient.Builder().addInterceptor(logging).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://agile-plains-64462.herokuapp.com")
                .addConverterFactory(JacksonConverterFactory.create())
                .client(httpClientWLogging)
                .build();

        WeatherService service = retrofit.create(WeatherService.class);
        service.currentCity("Milan").enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                Weather weather = response.body();
                Runnable r1 = () -> Log.e("Weather", "Lat: " + weather.getCoord().getLat());
                Runnable r2 = () -> Log.e("Weather", "Lon: " + weather.getCoord().getLon());
                r1.run();
                r2.run();
                Log.e("RESP", "SUCCESS");
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                Log.e("RESP", "FAILURE");
            }
        });
    }
}
