package com.saavor.user.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by a123456 on 19/01/18.
 */

public class RealtimeReturn {

    @SerializedName("Dishes")
    @Expose
    private String dishes;
    @SerializedName("LeftQtys")
    @Expose
    private String leftQtys;
    @SerializedName("ReturnCode")
    @Expose
    private String returnCode;
    @SerializedName("ReturnMessage")
    @Expose
    private String returnMessage;

    public String getDishes() {
        return dishes;
    }

    public void setDishes(String dishes) {
        this.dishes = dishes;
    }

    public String getLeftQtys() {
        return leftQtys;
    }

    public void setLeftQtys(String leftQtys) {
        this.leftQtys = leftQtys;
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
