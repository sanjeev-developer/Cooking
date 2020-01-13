package com.saavor.user.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by a123456 on 15/12/17.
 */

public class NotificationResponse {


    @SerializedName("NotificationList")
    @Expose
    private ArrayList<NotificationList> notificationList = null;
    @SerializedName("ReturnCode")
    @Expose
    private String returnCode;
    @SerializedName("ReturnMessage")
    @Expose
    private String returnMessage;

    public ArrayList<NotificationList> getNotificationList() {
        return notificationList;
    }

    public void setNotificationList(ArrayList<NotificationList> notificationList) {
        this.notificationList = notificationList;
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
