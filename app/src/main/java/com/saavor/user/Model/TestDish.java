package com.saavor.user.Model;

/**
 * Created by a123456 on 08/09/17.
 */

public class TestDish {

    public String Calories;
    public Integer DishId;
    public Integer IsCustomizable;
    public double Price;
    public Integer Quantity;
    private String ItemsName;
    private String ItemsCost;

    public String getCalories() {
        return Calories;
    }

    public void setCalories(String calories) {
        Calories = calories;
    }

    public Integer getDishId() {
        return DishId;
    }

    public void setDishId(Integer dishId) {
        DishId = dishId;
    }

    public Integer getIsCustomizable() {
        return IsCustomizable;
    }

    public void setIsCustomizable(Integer isCustomizable) {
        IsCustomizable = isCustomizable;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public Integer getQuantity() {
        return Quantity;
    }

    public void setQuantity(Integer quantity) {
        Quantity = quantity;
    }

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
}
