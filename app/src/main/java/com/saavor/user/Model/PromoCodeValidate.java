package com.saavor.user.Model;

public class PromoCodeValidate {

    private String CurrentDate;
    private String OrderAmount;
    private String PromoCode;
    private String SessionToken;
    private String UserId;

    public String getCurrentDate() {
        return CurrentDate;
    }

    public void setCurrentDate(String currentDate) {
        CurrentDate = currentDate;
    }

    public String getOrderAmount() {
        return OrderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        OrderAmount = orderAmount;
    }

    public String getPromoCode() {
        return PromoCode;
    }

    public void setPromoCode(String promoCode) {
        PromoCode = promoCode;
    }

    public String getSessionToken() {
        return SessionToken;
    }

    public void setSessionToken(String sessionToken) {
        SessionToken = sessionToken;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }
}
