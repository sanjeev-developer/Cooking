package com.saavor.user.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by a123456 on 11/11/17.
 */

public class OrderStatuses {

    @SerializedName("DeliveryTime")
    @Expose
    public String deliveryTime;
    @SerializedName("OrderID")
    @Expose
    public String orderID;
    @SerializedName("OrderStatusList")
    @Expose
    public ArrayList<OrderStatusList> orderStatusList = null;

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public ArrayList<OrderStatusList> getOrderStatusList() {
        return orderStatusList;
    }

    public void setOrderStatusList(ArrayList<OrderStatusList> orderStatusList) {
        this.orderStatusList = orderStatusList;
    }
}
