
package com.example.pablo.model.buy_one_order;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data implements Serializable
{

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("user_id")
    @Expose
    private Long userId;
    @SerializedName("order_id")
    @Expose
    private Long orderId;
    @SerializedName("room_name")
    @Expose
    private String roomName;
    @SerializedName("check_in")
    @Expose
    private String checkIn;
    @SerializedName("check_out")
    @Expose
    private String checkOut;
    @SerializedName("room_count")
    @Expose
    private String roomCount;
    @SerializedName("total_nights")
    @Expose
    private Double totalNights;
    @SerializedName("room_price_per_night")
    @Expose
    private Double roomPricePerNight;
    @SerializedName("room_has_offer")
    @Expose
    private Double roomHasOffer;
    @SerializedName("savings_per_room")
    @Expose
    private Double savingsPerRoom;
    @SerializedName("order_total_price")
    @Expose
    private Double orderTotalPrice;
    private final static long serialVersionUID = 3787303147222906261L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

    public String getRoomCount() {
        return roomCount;
    }

    public void setRoomCount(String roomCount) {
        this.roomCount = roomCount;
    }

    public Double getTotalNights() {
        return totalNights;
    }

    public void setTotalNights(Double totalNights) {
        this.totalNights = totalNights;
    }

    public Double getRoomPricePerNight() {
        return roomPricePerNight;
    }

    public void setRoomPricePerNight(Double roomPricePerNight) {
        this.roomPricePerNight = roomPricePerNight;
    }

    public Double getRoomHasOffer() {
        return roomHasOffer;
    }

    public void setRoomHasOffer(Double roomHasOffer) {
        this.roomHasOffer = roomHasOffer;
    }

    public Double getSavingsPerRoom() {
        return savingsPerRoom;
    }

    public void setSavingsPerRoom(Double savingsPerRoom) {
        this.savingsPerRoom = savingsPerRoom;
    }

    public Double getOrderTotalPrice() {
        return orderTotalPrice;
    }

    public void setOrderTotalPrice(Double orderTotalPrice) {
        this.orderTotalPrice = orderTotalPrice;
    }

}
