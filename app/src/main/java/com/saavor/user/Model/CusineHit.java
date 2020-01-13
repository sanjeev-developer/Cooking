package com.saavor.user.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by a123456 on 03/08/17.
 */

public class CusineHit {

    @SerializedName("CuisineList")
    @Expose
    public ArrayList<CuisineList> cuisineList = null;
    @SerializedName("MenuList")
    @Expose
    private ArrayList<MenuList> menuList = null;
    @SerializedName("ReturnCode")
    @Expose
    private String returnCode;
    @SerializedName("ReturnMessage")
    @Expose
    private String returnMessage;



    public ArrayList<CuisineList> getCuisineList() {
        return cuisineList;
    }

    public void setCuisineList(ArrayList<CuisineList> cuisineList) {
        this.cuisineList = cuisineList;
    }

    public ArrayList<MenuList> getMenuList() {
        return menuList;
    }

    public void setMenuList(ArrayList<MenuList> menuList) {
        this.menuList = menuList;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMessage() {
        return returnMessage;
    }

    public void setReturnMessage(String returnMessage) {
        this.returnMessage = returnMessage;
    }
}
