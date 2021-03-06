package com.example.pablo.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.pablo.R;
import com.example.pablo.activity.BookingInfo;
import com.example.pablo.activity.Login;
import com.example.pablo.interfaces.BookingInterface;
import com.example.pablo.interfaces.MyInterface;
import com.example.pablo.interfaces.Service;
import com.example.pablo.databinding.RoomCartBinding;
import com.example.pablo.model.cart.CartExample;
import com.example.pablo.model.cart.HotelOrderItem;

import java.util.List;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private List<HotelOrderItem> list;
    Context context;
    BookingInterface myInterface;


    public CartAdapter(Context context, BookingInterface myInterface) {
        this.context = context;
        this.myInterface = myInterface;
    }


    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RoomCartBinding binding = RoomCartBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CartAdapter.ViewHolder(binding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.binding.roomTotalPrice.setText(list.get(position).getOrderTotalPrice() + "");
        holder.binding.roomName.setText(list.get(position).getRoomName() + "");
        holder.binding.hotelRoomName.setText(list.get(position).getRoomCount() + " Room Count");
        holder.binding.dateTime.setText(list.get(position).getCheckIn() + " - " + list.get(position).getCheckOut());
        Glide.with(context).load(list.get(position).getRoomImage().get(0) )
                .transition(withCrossFade())
                .circleCrop()
                .apply(new RequestOptions().transform(new RoundedCorners(10))
                        .skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE))
                .error(R.drawable.annie_spratt_vg5lwaykixy_unsplash).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(holder.binding.imageView6);


        Double count = list.get(position).getOrderTotalPrice();
        Double c = Double.valueOf(0);
        for (int i = 0; i < list.size(); i++) {
            c += count;
        }

        myInterface.totalPriceOnItemClick(c);
        myInterface.countOnItemClick(Long.valueOf(list.size()));


    }



    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }


    public void setData(List<HotelOrderItem> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        RoomCartBinding binding;

        public ViewHolder(RoomCartBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }


    }


}
