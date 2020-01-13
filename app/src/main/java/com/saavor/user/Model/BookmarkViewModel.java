package com.saavor.user.Model;

/**
 * Created by a123456 on 22/06/17.
 */

public class BookmarkViewModel {

    public String CurrentDate;
    public String SessionToken;
    public String UserId;

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
