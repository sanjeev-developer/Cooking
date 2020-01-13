package com.saavor.user.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by a123456 on 29/08/17.
 */

public class TodayMenuHit {

    @SerializedName("CurrentDate")
    @Expose
    private String currentDate;
    @SerializedName("ProfileId")
    @Expose
    private String profileId;
    @SerializedName("SessionToken")
    @Expose
    private String sessionToken;
    @SerializedName("UserId")
    @Expose
    private String userId;

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
