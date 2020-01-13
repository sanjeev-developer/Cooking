package com.saavor.user.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by nariendersingh on 25/10/17.
 */

public class OrderStatusResponse {


    @SerializedName("OrderStatuses")
    @Expose
    public OrderStatuses orderStatuses;
    @SerializedName("ReturnCode")
    @Expose
    public String returnCode;
    @SerializedName("ReturnMessage")
    @Expose
    public String returnMessage;

    public OrderStatuses getOrderStatuses() {
        return orderStatuses;
    }

    public void setOrderStatuses(OrderStatuses orderStatuses) {
        this.orderStatuses = orderStatuses;
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
