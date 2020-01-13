package com.saavor.user.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by a123456 on 11/09/17.
 */

public class OrderDish {

    @SerializedName("CustomItemsCost")
    @Expose
    private String customItemsCost;
    @SerializedName("CustomItemsName")
    @Expose
    private String customItemsName;
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
    private float price;
    @SerializedName("Quantity")
    @Expose
    private Integer quantity;

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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
