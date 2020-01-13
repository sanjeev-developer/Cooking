package com.saavor.user.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by a123456 on 09/09/17.
 */

public class CartItem {

    @SerializedName("CustomItemsCost")
    @Expose
    private String customItemsCost;
    @SerializedName("CustomItemsName")
    @Expose
    private String customItemsName;
    @SerializedName("DealDiscount")
    @Expose
    private Double dealDiscount;
    @SerializedName("DishId")
    @Expose
    private Integer dishId;
    @SerializedName("DishName")
    @Expose
    private String dishName;
    @SerializedName("IsCustomizable")
    @Expose
    private Integer isCustomizable;
    @SerializedName("Price")
    @Expose
    private double price;

    public String getPreparingTime() {
        return preparingTime;
    }

    public void setPreparingTime(String preparingTime) {
        this.preparingTime = preparingTime;
    }

    @SerializedName("PreparingTime")
    @Expose
    private String preparingTime;

    public Integer getAvailableQty() {
        return availableQty;
    }

    public void setAvailableQty(Integer availableQty) {
        this.availableQty = availableQty;
    }

    @SerializedName("PriceAfterDiscount")
    @Expose

    private Double priceAfterDiscount;
    @SerializedName("AvailableQty")
    @Expose
    private Integer availableQty;

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    @SerializedName("Quantity")
    @Expose
    private Integer quantity;
    @SerializedName("Calories")
    @Expose
    private String calories;

    public String getCustomItemsCost() {
        return customItemsCost;
    }

    public void setCustomItemsCost(String customItemsCost) {
        this.customItemsCost = customItemsCost;
    }

    public String getCustomItemsName() {
        return customItemsName;
    }

    public void setCustomItemsName(String customItemsName) {
        this.customItemsName = customItemsName;
    }

    public Double getDealDiscount() {
        return dealDiscount;
    }

    public void setDealDiscount(Double dealDiscount) {
        this.dealDiscount = dealDiscount;
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

    public Integer getIsCustomizable() {
        return isCustomizable;
    }

    public void setIsCustomizable(Integer isCustomizable) {
        this.isCustomizable = isCustomizable;
    }

    public Double getPriceAfterDiscount() {
        return priceAfterDiscount;
    }

    public void setPriceAfterDiscount(Double priceAfterDiscount) {
        this.priceAfterDiscount = priceAfterDiscount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
