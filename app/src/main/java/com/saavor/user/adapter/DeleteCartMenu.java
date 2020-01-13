package com.saavor.user.adapter;

/**
 * Created by a123456 on 14/09/17.
 */

public class DeleteCartMenu {

    public Integer DishId;
    public KitchenAdapter.MyViewHolder Holder;
    public Integer Position;
    public Integer IsCustomizable;
    public double deleteprice;
    public String itemname;
    public Integer Itemleft;

    public Integer getItemleft() {
        return Itemleft;
    }

    public void setItemleft(Integer itemleft) {
        Itemleft = itemleft;
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

    public Integer getIsCustomizable() {
        return IsCustomizable;
    }

    public void setIsCustomizable(Integer isCustomizable) {
        IsCustomizable = isCustomizable;
    }

    public double getDeleteprice() {
        return deleteprice;
    }

    public void setDeleteprice(double deleteprice) {
        this.deleteprice = deleteprice;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }
}
