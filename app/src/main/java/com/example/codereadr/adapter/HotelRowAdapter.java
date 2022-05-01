package com.example.codereadr.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.codereadr.R;
import com.example.codereadr.models.Business;
import com.example.codereadr.models.HotelModel;
import com.squareup.picasso.Picasso;

public class HotelRowAdapter extends RecyclerView.Adapter<HotelRowAdapter.HotelDetailsHolder>{

    private HotelModel hotels = new HotelModel();
    private Context context;
    @NonNull
    @Override
    public HotelDetailsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_hotel_detail_row, parent, false);
        context = parent.getContext();
        return new HotelDetailsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HotelDetailsHolder holder, int position) {
        Business hotel = hotels.getBusinesses().get(position);

        if(hotel.getName() != null)
            holder.hotelName.setText(hotel.getName());
        if(hotel.getLocation() != null && hotel.getLocation().getAddress1() != null)
            holder.hotelAddress.setText(hotel.getLocation().getAddress1());
        if(hotel.getIsClosed() != null)
            holder.isOpen.setText(hotel.getIsClosed() ? "Currently Closed" : "Currently Open");
        if(hotel.getRating() != null)
            holder.hotelRating.setText(hotel.getRating().toString());

        Picasso.get()
                .load(hotel.getImageUrl().isEmpty() ? null : hotel.getImageUrl())
                .placeholder(R.drawable.ic_baseline_image_24)
                .error(R.drawable.ic_baseline_terrain_24)
                .into(holder.hotelImage);

    }

    public void setHotels(HotelModel hotels) {
        this.hotels = hotels;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return this.hotels.getBusinesses().size();
    }

    static class HotelDetailsHolder extends RecyclerView.ViewHolder {

        private ImageView hotelImage;
        private TextView hotelName;
        private TextView hotelAddress;
        private TextView isOpen;
        private TextView hotelRating;

        public HotelDetailsHolder(View itemView) {
            super(itemView);
            hotelImage = itemView.findViewById(R.id.hotel_image);
            hotelName = itemView.findViewById(R.id.hotel_name);
            hotelAddress = itemView.findViewById(R.id.hotel_address);
            isOpen = itemView.findViewById(R.id.is_open);
            hotelRating = itemView.findViewById(R.id.hotel_rating);
        }
    }
}
