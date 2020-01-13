package com.saavor.user.Model;

/**
 * Created by a123456 on 22/06/17.
 */

public class BookmarkModel {

    public String ProfileId;
    public String SessionToken;
    public String UserId;


    public String getProfileId() {
        return ProfileId;
    }

    public void setProfileId(String profileId) {
        ProfileId = profileId;
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
