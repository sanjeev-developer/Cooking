package com.saavor.user.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by a123456 on 31/10/17.
 */

public class FavDishList {

    @SerializedName("AvgDeliveryTime")
    @Expose
    private Integer avgDeliveryTime;
    @SerializedName("DeliveryCharges")
    @Expose
    private Double deliveryCharges;
    @SerializedName("DishId")
    @Expose
    private Integer dishId;
    @SerializedName("DishName")
    @Expose
    private String dishName;
    @SerializedName("IsOpen")
    @Expose
    private Integer isOpen;
    @SerializedName("KitchenName")
    @Expose
    private String kitchenName;
    @SerializedName("ProfileId")
    @Expose
    private Integer profileId;

    public Integer getAvgDeliveryTime() {
        return avgDeliveryTime;
    }

    public void setAvgDeliveryTime(Integer avgDeliveryTime) {
        this.avgDeliveryTime = avgDeliveryTime;
    }

    public Double getDeliveryCharges() {
        return deliveryCharges;
    }

    public void setDeliveryCharges(Double deliveryCharges) {
        this.deliveryCharges = deliveryCharges;
    }

    public Integer getDishId() {
        return dishId;
    }

    public void setDishId(Integer dishId) {
        this.dishId = dishId;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public Integer getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Integer isOpen) {
        this.isOpen = isOpen;
    }

    public String getKitchenName() {
        return kitchenName;
    }

    public void setKitchenName(String kitchenName) {
        this.kitchenName = kitchenName;
    }

    public Integer getProfileId() {
        return profileId;
    }

    public void setProfileId(Integer profileId) {
        this.profileId = profileId;
    }

}
