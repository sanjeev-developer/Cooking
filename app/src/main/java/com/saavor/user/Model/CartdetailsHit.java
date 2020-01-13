package com.saavor.user.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by a123456 on 09/09/17.
 */

public class CartdetailsHit {

    @SerializedName("CartInfo")
    @Expose
    private CartInfo cartInfo;
    @SerializedName("ReturnCode")
    @Expose
    private String returnCode;
    @SerializedName("ReturnMessage")
    @Expose
    private String returnMessage;

    public CartInfo getCartInfo() {
        return cartInfo;
    }

    public void setCartInfo(CartInfo cartInfo) {
        this.cartInfo = cartInfo;
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
