package com.saavor.user.Model;

/**
 * Created by user on 15-12-2017.
 */

public class GetChefProfile {

    private String ChefId;
    private String CurrentDate;
    private String SessionToken;
    private String UserId;

    public String getChefId() {
        return ChefId;
    }

    public void setChefId(String chefId) {
        ChefId = chefId;
    }

    public String getCurrentDate() {
        return CurrentDate;
    }

    public void setCurrentDate(String currentDate) {
        CurrentDate = currentDate;
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
