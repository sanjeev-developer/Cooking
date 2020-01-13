package com.saavor.user.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by a123456 on 30/08/17.
 */

public class CustomDishItem {

    @SerializedName("Cost")
    @Expose
    private double cost;
    @SerializedName("ItemName")
    @Expose
    private String itemName;

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
