package com.example.azapriy.retrojackson;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends ListActivity {
    public static final String WEATHER_SERVICE_URL = "https://agile-plains-64462.herokuapp.com/current?city=%s";
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClientWLogging = new OkHttpClient.Builder().addInterceptor(logging).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://agile-plains-64462.herokuapp.com")
                .addConverterFactory(JacksonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(httpClientWLogging)
                .build();

        ArrayList<String> data = new ArrayList<>();

        WeatherService service = retrofit.create(WeatherService.class);
        String[] cities = new String[] {"Milan", "London"};
        Observable.from(cities)
                .flatMap(service::currentCity)
                .map(Object::toString)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onNext(String weather) {
                        data.add(weather);
                    }

                    @Override
                    public void onCompleted() {
                        mAdapter = new ArrayAdapter<>(MainActivity.this, R.layout.text_view, data);
                        setListAdapter(mAdapter);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("ERROR" ,e.toString());
                    }
                });
    }
}
