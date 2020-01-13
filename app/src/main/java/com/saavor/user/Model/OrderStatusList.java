package com.saavor.user.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by nariendersingh on 25/10/17.
 */

public class OrderStatusList {

    @SerializedName("OrderModifiedDate")
    @Expose
    private String orderModifiedDate;
    @SerializedName("OrderStatus")
    @Expose
    private String orderStatus;

    public String getOrderModifiedDate() {
        return orderModifiedDate;
    }

    public void setOrderModifiedDate(String orderModifiedDate) {
        this.orderModifiedDate = orderModifiedDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
