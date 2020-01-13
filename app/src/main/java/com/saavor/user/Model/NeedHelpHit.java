package com.saavor.user.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by a123456 on 11/09/17.
 */

public class NeedHelpHit {

    @SerializedName("CreateDate")
    @Expose
    private String createDate;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("FoodOrderId")
    @Expose
    private String foodOrderId;
    @SerializedName("SessionToken")
    @Expose
    private String sessionToken;
    @SerializedName("TopicName")
    @Expose
    private String topicName;
    @SerializedName("TopicTitle")
    @Expose
    private String topicTitle;
    @SerializedName("UserId")
    @Expose
    private String userId;

    public String getCreateDate()
    {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFoodOrderId() {
        return foodOrderId;
    }

    public void setFoodOrderId(String foodOrderId) {
        this.foodOrderId = foodOrderId;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getTopicTitle() {
        return topicTitle;
    }

    public void setTopicTitle(String topicTitle) {
        this.topicTitle = topicTitle;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
