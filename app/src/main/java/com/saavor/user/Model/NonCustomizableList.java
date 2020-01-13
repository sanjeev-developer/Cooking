package com.saavor.user.Model;

import com.saavor.user.adapter.KitchenAdapter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by a123456 on 06/09/17.
 */

public class NonCustomizableList {

    public Integer DishId;
    public KitchenAdapter.MyViewHolder Holder;
    public Integer Position;
    public Integer Itemleft;
    public Integer Quantity;

    public String getPreprationtime() {
        return preprationtime;
    }

    public void setPreprationtime(String preprationtime) {
        this.preprationtime = preprationtime;
    }

    public String Dishname;
    public String Calories;
    public double Price;
    public Integer avilquantity;
    public Integer IsCustomizable;
    public String preprationtime;

    public Integer getAvilquantity() {
        return avilquantity;
    }

    public void setAvilquantity(Integer avilquantity) {
        this.avilquantity = avilquantity;
    }

    public double Updatedprice;
    @SerializedName("ItemsName")
    @Expose
    private String ItemsName;

    public double getUpdatedprice() {
        return Updatedprice;
    }

    public void setUpdatedprice(double updatedprice) {
        Updatedprice = updatedprice;
    }

    @SerializedName("ItemsCost")
    @Expose
    private String ItemsCost;

    public String getItemsName() {
        return ItemsName;
    }

    public void setItemsName(String itemsName) {
        ItemsName = itemsName;
    }

    public String getItemsCost() {
        return ItemsCost;
    }

    public void setItemsCost(String itemsCost) {
        ItemsCost = itemsCost;
    }

    public Integer getDishId() {
        return DishId;
    }

    public void setDishId(Integer dishId) {
        DishId = dishId;
    }

    public KitchenAdapter.MyViewHolder getHolder() {
        return Holder;
    }

    public void setHolder(KitchenAdapter.MyViewHolder holder) {
        Holder = holder;
    }

    public Integer getPosition() {
        return Position;
    }

    public void setPosition(Integer position) {
        Position = position;
    }

    public Integer getItemleft() {
        return Itemleft;
    }

    public void setItemleft(Integer itemleft) {
        Itemleft = itemleft;
    }

    public Integer getQuantity() {
        return Quantity;
    }

    public void setQuantity(Integer quantity) {
        Quantity = quantity;
    }

    public String getDishname() {
        return Dishname;
    }

    public void setDishname(String dishname) {
        Dishname = dishname;
    }

    public String getCalories() {
        return Calories;
    }

    public void setCalories(String calories) {
        Calories = calories;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public Integer getIsCustomizable() {
        return IsCustomizable;
    }

    public void setIsCustomizable(Integer isCustomizable) {
        IsCustomizable = isCustomizable;
    }
}
