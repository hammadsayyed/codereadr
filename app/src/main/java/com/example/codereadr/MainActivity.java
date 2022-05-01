package com.example.codereadr;

import android.os.Bundle;

import com.example.codereadr.adapter.HotelRowAdapter;
import com.example.codereadr.models.HotelModel;
import com.google.android.material.slider.Slider;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.DisplayMetrics;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.codereadr.databinding.ActivityMainBinding;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    TextView range_tv;
    RecyclerView hotelRecyclerView;
    HotelRowAdapter hotelRowAdapter;
    HotelModel hotelDetails;
    Slider slider;
    SwipeRefreshLayout swipeContainer;
    int page = 0;
    String radius;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        range_tv = binding.rangeSelectedTv;
        slider = binding.slider;
        swipeContainer = binding.swipeContainer;
        hotelRecyclerView = binding.hotelRecyclerView;
        hotelRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        hotelRowAdapter = new HotelRowAdapter();
        hotelRecyclerView.setAdapter(hotelRowAdapter);
        radius = Integer.toString((int) slider.getValue());
        range_tv.setText(radius);
        searchHotels(radius, null);


        slider.addOnSliderTouchListener(new Slider.OnSliderTouchListener() {
            @Override
            public void onStartTrackingTouch(@NonNull Slider slider) {
            }

            @Override
            public void onStopTrackingTouch(@NonNull Slider slider) {
                radius = Integer.toString((int) slider.getValue());
                searchHotels(radius, null);
                range_tv.setText(String.valueOf(slider.getValue()/1000));
            }
        });

        swipeContainer.setOnRefreshListener(() -> searchHotels(radius, null));

        hotelRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1) && !swipeContainer.isRefreshing()) {
                    searchHotels(radius, String.valueOf(++page));
                }
            }
        });
    }

    void searchHotels(@Nullable String radius, @Nullable String page){
        showLoader(page != null);
        Call<HotelModel> call = MainApp.retrofitCalls.search(Constants.API_KEY,
                Constants.DEMO_CITY,
                "15",
                radius,
                "distance",
                "restaurants",
                page
                );
        call.enqueue(new Callback<HotelModel>(){

            @Override
            public void onResponse(@NonNull Call<HotelModel> call, @NonNull Response<HotelModel> response) {
                if(response.body() != null) {
                    HotelModel hotelDetailsTemp = response.body();
                    if(page != null){
                        hotelDetails.addBusinesses(hotelDetailsTemp.getBusinesses());
                    }else{
                        hotelDetails = hotelDetailsTemp;
                    }
                    hotelRowAdapter.setHotels(hotelDetails);
                    hideLoader();
                }
                else {
                    hideLoader();
                    Toast.makeText(getApplicationContext(), "Null response error has occurred", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<HotelModel> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(), "An error has occurred", Toast.LENGTH_LONG).show();
            }
        });
    }

    void showLoader(Boolean showAtBottom){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int absoluteBottom = height - 600;
        if(showAtBottom) {
            swipeContainer.setProgressViewOffset(false, 0, absoluteBottom);
        }
        else {
            swipeContainer.setProgressViewOffset(false, 0, hotelRecyclerView.getTop() + 200);
        }
        swipeContainer.setRefreshing(true);
    }

    void hideLoader(){
        swipeContainer.setRefreshing(false);
        swipeContainer.setProgressViewOffset(false, 0, hotelRecyclerView.getTop() + 200);   //Resetting the progressbar to default position.
    }
}