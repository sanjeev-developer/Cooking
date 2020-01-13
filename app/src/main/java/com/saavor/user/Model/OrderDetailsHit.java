package com.saavor.user.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by a123456 on 11/09/17.
 */

public class OrderDetailsHit {

    @SerializedName("CurrentDate")
    @Expose
    public String currentDate;
    @SerializedName("OrderId")
    @Expose
    public String orderId;
    @SerializedName("SessionToken")
    @Expose
    public String sessionToken;
    @SerializedName("UserId")
    @Expose
    public String userId;

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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
