package com.saavor.user.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by a123456 on 29/08/17.
 */

public class TodaysMenu {

    @SerializedName("DishList")
    @Expose
    public ArrayList<DishList> dishList = null;
    @SerializedName("MenuTitle")
    @Expose
    public String menuTitle;

    public ArrayList<DishList> getDishList() {
        return dishList;
    }

    public void setDishList(ArrayList<DishList> dishList) {
        this.dishList = dishList;
    }

    public String getMenuTitle() {
        return menuTitle;
    }

    public void setMenuTitle(String menuTitle) {
        this.menuTitle = menuTitle;
    }

}
