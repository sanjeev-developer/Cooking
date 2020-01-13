package com.saavor.user.Model;

/**
 * Created by a123456 on 03/01/18.
 */

public class GetBookingHistory {

    private String CurrentDate;
    private String SessionToken;
    private String UserId;

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
