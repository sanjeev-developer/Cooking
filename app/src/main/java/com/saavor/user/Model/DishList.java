package com.saavor.user.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by a123456 on 29/08/17.
 */

public class DishList {

    @SerializedName("AvailableQty")
    @Expose
    private Integer availableQty;
    @SerializedName("Calories")
    @Expose
    private String calories;
    @SerializedName("Category")
    @Expose
    private String category;
    @SerializedName("CuisineName")
    @Expose
    private String cuisineName;
    @SerializedName("Customizable")
    @Expose
    private Integer customizable;
    @SerializedName("DishId")
    @Expose
    private Integer dishId;
    @SerializedName("DishName")
    @Expose
    private String dishName;
    @SerializedName("KitchenName")
    @Expose
    private String kitchenName;

    public String getPreparingTime() {
        return preparingTime;
    }

    public void setPreparingTime(String preparingTime) {
        this.preparingTime = preparingTime;
    }

    @SerializedName("Price")
    @Expose
    private float price;
    @SerializedName("ProfileId")
    @Expose
    private Integer profileId;
    @SerializedName("PreparingTime")
    @Expose
    private String preparingTime;

    public Integer getAvailableQty() {
        return availableQty;
    }

    public void setAvailableQty(Integer availableQty) {
        this.availableQty = availableQty;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCuisineName() {
        return cuisineName;
    }

    public void setCuisineName(String cuisineName) {
        this.cuisineName = cuisineName;
    }

    public Integer getCustomizable() {
        return customizable;
    }

    public void setCustomizable(Integer customizable) {
        this.customizable = customizable;
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

    public String getKitchenName() {
        return kitchenName;
    }

    public void setKitchenName(String kitchenName) {
        this.kitchenName = kitchenName;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Integer getProfileId() {
        return profileId;
    }

    public void setProfileId(Integer profileId) {
        this.profileId = profileId;
    }
}