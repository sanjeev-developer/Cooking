package com.saavor.user.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by a123456 on 11/08/17.
 */

public class KitchenInfoReturn {

    @SerializedName("ReturnCode")
    @Expose
    private String returnCode;
    @SerializedName("ReturnMessage")
    @Expose
    private String returnMessage;
    @SerializedName("kitchenInfo")
    @Expose
    public KitchenInfo kitchenInfo;

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

    public KitchenInfo getKitchenInfo() {
        return kitchenInfo;
    }

    public void setKitchenInfo(KitchenInfo kitchenInfo) {
        this.kitchenInfo = kitchenInfo;
    }

}

