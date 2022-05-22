
package com.example.pablo.model.cart;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CartExample implements Serializable
{
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("hotel_order_items")
    @Expose
    private List<HotelOrderItem> hotelOrderItems = null;
    private final static long serialVersionUID = -2482383789098546268L;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public List<HotelOrderItem> getHotelOrderItems() {
        return hotelOrderItems;
    }

    public void setHotelOrderItems(List<HotelOrderItem> hotelOrderItems) {
        this.hotelOrderItems = hotelOrderItems;
    }

}