package com.example.codereadr.services;

import com.example.codereadr.models.HotelModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitCalls {
    @GET("v3/businesses/search")
    Call<HotelModel> search(@Header("Authorization") String authHeader,
                            @Query("location") String location,
                            @Query("limit") String limit,
                            @Query("radius") String radius,
                            @Query("sort_by") String sort_by,
                            @Query("term") String term,
                            @Query("offset") String offset
    );
}
