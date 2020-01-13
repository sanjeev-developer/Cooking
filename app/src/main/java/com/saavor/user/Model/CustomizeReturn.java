package com.saavor.user.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by a123456 on 30/08/17.
 */

public class CustomizeReturn {

    @SerializedName("KitchenTodayDish")
    @Expose
    public KitchenTodayDish kitchenTodayDish;
    @SerializedName("ReturnCode")
    @Expose
    private String returnCode;
    @SerializedName("ReturnMessage")
    @Expose
    private String returnMessage;

    public KitchenTodayDish getKitchenTodayDish() {
        return kitchenTodayDish;
    }

    public void setKitchenTodayDish(KitchenTodayDish kitchenTodayDish) {
        this.kitchenTodayDish = kitchenTodayDish;
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
