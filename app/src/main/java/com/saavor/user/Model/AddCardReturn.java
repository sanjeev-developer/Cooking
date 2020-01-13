package com.saavor.user.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by a123456 on 12/10/17.
 */

public class AddCardReturn {

    @SerializedName("CardId")
    @Expose
    private String cardId;
    @SerializedName("CustomerId")
    @Expose
    private String customerId;
    @SerializedName("ReturnCode")
    @Expose
    private String returnCode;
    @SerializedName("ReturnMessage")
    @Expose
    private String returnMessage;
    @SerializedName("StripeCardId")
    @Expose
    private String stripeCardId;

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
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

    public String getStripeCardId() {
        return stripeCardId;
    }

    public void setStripeCardId(String stripeCardId) {
        this.stripeCardId = stripeCardId;
    }
}
