package com.saavor.user.Model;

import com.saavor.user.adapter.KitchenAdapter;

import java.util.ArrayList;

/**
 * Created by a123456 on 06/09/17.
 */

public class CustomizableList {

    public Integer DishId;
    public KitchenAdapter.MyViewHolder Holder;
    public int Position;
    public int Itemleft;
    public Integer Quantity;
    public String Dishname;
    public double Dishprice;
    public double Dishupdatedprice;
    public String Calories;
    public ArrayList<AddOn>addOns;
    public String getCalories() {
        return Calories;
    }

    public void setCalories(String calories) {
        Calories = calories;
    }

    public Integer getQuantity() {
        return Quantity;
    }

    public void setQuantity(Integer quantity) {
        Quantity = quantity;
    }

    public Integer getDishId() {
        return DishId;
    }

    public void setDishId(Integer dishId) {
        DishId = dishId;
    }

    public ArrayList<AddOn> getAddOns() {
        return addOns;
    }

    public void setAddOns(ArrayList<AddOn> addOns) {
        this.addOns = addOns;
    }

    public KitchenAdapter.MyViewHolder getHolder() {
        return Holder;
    }

    public void setHolder(KitchenAdapter.MyViewHolder holder) {
        Holder = holder;
    }

    public int getPosition() {
        return Position;
    }

    public void setPosition(int position) {
        Position = position;
    }

    public int getItemleft() {
        return Itemleft;
    }

    public void setItemleft(int itemleft) {
        Itemleft = itemleft;
    }

    public String getDishname() {
        return Dishname;
    }

    public void setDishname(String dishname) {
        Dishname = dishname;
    }

    public double getDishprice() {
        return Dishprice;
    }

    public void setDishprice(double dishprice) {
        Dishprice = dishprice;
    }

    public double getDishupdatedprice() {
        return Dishupdatedprice;
    }

    public void setDishupdatedprice(double dishupdatedprice) {
        Dishupdatedprice = dishupdatedprice;
    }



}
