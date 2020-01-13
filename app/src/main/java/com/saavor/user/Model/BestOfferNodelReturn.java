package com.saavor.user.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by a123456 on 03/08/17.
 */

public class BestOfferNodelReturn {

    @SerializedName("KitchensDealList")
    @Expose
    public ArrayList<KitchensDealList> kitchensDealList = null;
    @SerializedName("ReturnCode")
    @Expose
    private String returnCode;
    @SerializedName("ReturnMessage")
    @Expose
    private String returnMessage;

    public ArrayList<KitchensDealList> getKitchensDealList() {
        return kitchensDealList;
    }

    public void setKitchensDealList(ArrayList<KitchensDealList> kitchensDealList) {
        this.kitchensDealList = kitchensDealList;
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
