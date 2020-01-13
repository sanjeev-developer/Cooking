package com.saavor.user.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by a123456 on 01/09/17.
 */

public class FavouriteReturn {
    @SerializedName("FavDishList")
    @Expose
    private ArrayList<FavDishList> favDishList = null;
    @SerializedName("ReturnCode")
    @Expose
    private String returnCode;
    @SerializedName("ReturnMessage")
    @Expose
    private String returnMessage;

    public ArrayList<FavDishList> getFavDishList() {
        return favDishList;
    }

    public void setFavDishList(ArrayList<FavDishList> favDishList) {
        this.favDishList = favDishList;
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
