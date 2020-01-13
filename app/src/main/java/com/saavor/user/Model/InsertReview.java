package com.saavor.user.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by a123456 on 06/09/17.
 */

public class InsertReview {

    @SerializedName("FoodOrderId")
    @Expose
    private String foodOrderId;
    @SerializedName("ProfileId")
    @Expose
    private String profileId;
    @SerializedName("ReviewDate")
    @Expose
    private String reviewDate;
    @SerializedName("Reviews")
    @Expose
    private String reviews;
    @SerializedName("SessionToken")
    @Expose
    private String sessionToken;
    @SerializedName("Stars")
    @Expose
    private String stars;
    @SerializedName("UserId")
    @Expose
    private String userId;

    public String getFoodOrderId() {
        return foodOrderId;
    }

    public void setFoodOrderId(String foodOrderId) {
        this.foodOrderId = foodOrderId;
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public String getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(String reviewDate) {
        this.reviewDate = reviewDate;
    }

    public String getReviews() {
        return reviews;
    }

    public void setReviews(String reviews) {
        this.reviews = reviews;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public String getStars() {
        return stars;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
