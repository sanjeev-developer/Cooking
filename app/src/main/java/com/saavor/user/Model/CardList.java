package com.saavor.user.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by a123456 on 15/09/17.
 */

public class CardList {

    @SerializedName("CardId")
    @Expose
    private Integer cardId;
    @SerializedName("CardNumber")
    @Expose
    private String cardNumber;
    @SerializedName("CardType")
    @Expose
    private String cardType;
    @SerializedName("CustomerId")
    @Expose
    private String customerId;
    @SerializedName("StripeCardId")
    @Expose
    private String stripeCardId;

    public Integer getCardId() {
        return cardId;
    }

    public void setCardId(Integer cardId) {
        this.cardId = cardId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getStripeCardId() {
        return stripeCardId;
    }

    public void setStripeCardId(String stripeCardId) {
        this.stripeCardId = stripeCardId;
    }
}
