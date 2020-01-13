package com.saavor.user.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by a123456 on 29/08/17.
 */

public class TodayReturn
{


    @SerializedName("ReturnCode")
    @Expose
    private String returnCode;
    @SerializedName("ReturnMessage")
    @Expose
    private String returnMessage;

    public String getFoodOrderId() {
        return FoodOrderId;
    }

    public void setFoodOrderId(String foodOrderId) {
        FoodOrderId = foodOrderId;
    }

    @SerializedName("FoodOrderId")
    @Expose
    private String FoodOrderId;
    @SerializedName("TodaysMenu")
    @Expose
    private ArrayList<TodaysMenu> todaysMenu = null;

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public ArrayList<TodaysMenu> getTodaysMenu() {
        return todaysMenu;
    }

    public void setTodaysMenu(ArrayList<TodaysMenu> todaysMenu) {
        this.todaysMenu = todaysMenu;
    }

    public String getReturnMessage() {
        return returnMessage;
    }

    public void setReturnMessage(String returnMessage) {
        this.returnMessage = returnMessage;
    }


}
