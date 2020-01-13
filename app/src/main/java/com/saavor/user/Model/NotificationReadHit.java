package com.saavor.user.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by a123456 on 01/01/18.
 */

public class NotificationReadHit {

    @SerializedName("IsRead")
    @Expose
    private Integer isRead;
    @SerializedName("NotificationIds")
    @Expose
    private String notificationIds;
    @SerializedName("SessionToken")
    @Expose
    private String sessionToken;
    @SerializedName("UserId")
    @Expose
    private String userId;

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }

    public String getNotificationIds() {
        return notificationIds;
    }

    public void setNotificationIds(String notificationIds) {
        this.notificationIds = notificationIds;
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
