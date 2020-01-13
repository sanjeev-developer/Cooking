package com.saavor.user.Model;

/**
 * Created by a123456 on 15/12/17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationList {

    @SerializedName("CreateDate")
    @Expose
    private String createDate;
    @SerializedName("IsRead")
    @Expose
    private Integer isRead;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("NotificationId")
    @Expose
    private Integer notificationId;
    @SerializedName("NotificationType")
    @Expose
    private String notificationType;
    @SerializedName("Title")
    @Expose
    private String title;

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Integer notificationId) {
        this.notificationId = notificationId;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
