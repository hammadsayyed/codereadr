package com.example.codereadr;

import android.app.Application;
import android.util.Log;

import com.example.codereadr.services.RetrofitCalls;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import kotlin.jvm.Synchronized;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainApp extends Application {
    static RetrofitCalls retrofitCalls;
    private final String TAG = MainApp.class.getName();
    static private MainApp mInstance;
    @Override
    public void onCreate() {
        super.onCreate();
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                 .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory
                        .create(new GsonBuilder().serializeNulls()
                                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                                .create()));

        retrofitBuilder.client(new OkHttpClient.Builder()
                .connectTimeout(180, TimeUnit.SECONDS)
                .writeTimeout(180, TimeUnit.SECONDS)
                .readTimeout(180, TimeUnit.SECONDS)
                .addInterceptor(new HttpLoggingInterceptor()
                        .setLevel(HttpLoggingInterceptor.Level.BODY)).build());

        Retrofit retrofit = retrofitBuilder.build();
        retrofitCalls = retrofit.create(RetrofitCalls.class);
        mInstance = this;

    }

    @Synchronized
    public static MainApp getInstance(){
        return mInstance;
    }

}
