package com.saavor.user.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by a123456 on 03/11/17.
 */

public class Cancelorder {

    @SerializedName("CurrentDate")
    @Expose
    private String currentDate;
    @SerializedName("FoodOrderId")
    @Expose
    private String foodOrderId;
    @SerializedName("SessionToken")
    @Expose
    private String sessionToken;
    @SerializedName("UserId")
    @Expose
    private String userId;

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }


    public String getFoodOrderId() {
        return foodOrderId;
    }

    public void setFoodOrderId(String foodOrderId) {
        this.foodOrderId = foodOrderId;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
