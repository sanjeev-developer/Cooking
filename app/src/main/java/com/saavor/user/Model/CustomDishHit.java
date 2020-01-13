package com.saavor.user.Model;

/**
 * Created by a123456 on 13/09/17.
 */

public class CustomDishHit {

    public String DishId;
    public String ItemName;
    public String Cost;
    public int Quantity;

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public String getDishId() {
        return DishId;
    }

    public void setDishId(String dishId) {
        DishId = dishId;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getCost() {
        return Cost;
    }

    public void setCost(String cost) {
        Cost = cost;
    }
}
