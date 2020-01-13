package com.saavor.user.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by a123456 on 15/09/17.
 */

public class DeleteCardHit {

    @SerializedName("CardId")
    @Expose
    private String cardId;
    @SerializedName("CustomerId")
    @Expose
    private String customerId;
    @SerializedName("SessionToken")
    @Expose
    private String sessionToken;
    @SerializedName("StripeCardId")
    @Expose
    private String stripeCardId;
    @SerializedName("UserId")
    @Expose
    private String userId;

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

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public String getStripeCardId() {
        return stripeCardId;
    }

    public void setStripeCardId(String stripeCardId) {
        this.stripeCardId = stripeCardId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
