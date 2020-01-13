package com.saavor.user.Model;

/**
 * Created by a123456 on 28/07/17.
 */
import java.util.ArrayList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class KitchenSearchReturn {

    @SerializedName("KitchenList")
    @Expose
    private ArrayList<KitchenList> kitchenList = null;
    @SerializedName("ReturnCode")
    @Expose
    private String returnCode;
    @SerializedName("ReturnMessage")
    @Expose
    private String returnMessage;

    public ArrayList<KitchenList> getKitchenList() {
        return kitchenList;
    }

    public void setKitchenList(ArrayList<KitchenList> kitchenList) {
        this.kitchenList = kitchenList;
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
