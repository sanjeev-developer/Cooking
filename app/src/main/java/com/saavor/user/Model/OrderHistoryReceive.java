package com.saavor.user.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by a123456 on 09/09/17.
 */

public class OrderHistoryReceive {

    @SerializedName("OrderList")
    @Expose
    private ArrayList<OrderList> orderList = null;
    @SerializedName("ReturnCode")
    @Expose
    private String returnCode;
    @SerializedName("ReturnMessage")
    @Expose

    private String returnMessage;


    public ArrayList<OrderList> getOrderList() {
        return orderList;
    }

    public void setOrderList(ArrayList<OrderList> orderList) {
        this.orderList = orderList;
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
