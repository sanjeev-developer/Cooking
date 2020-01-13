package com.saavor.user.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by a123456 on 12/09/17.
 */

public class ReviewList {

    @SerializedName("Days")
    @Expose
    private Integer days;
    @SerializedName("Hours")
    @Expose
    private Integer hours;
    @SerializedName("Minutes")
    @Expose
    private Integer minutes;
    @SerializedName("ProfilePicPath")
    @Expose
    private String profilePicPath;
    @SerializedName("ReviewUTCDate")
    @Expose
    private String reviewUTCDate;
    @SerializedName("Reviews")
    @Expose
    private String reviews;
    @SerializedName("Stars")
    @Expose
    private Integer stars;
    @SerializedName("UserName")
    @Expose
    private String userName;

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    public Integer getMinutes() {
        return minutes;
    }

    public void setMinutes(Integer minutes) {
        this.minutes = minutes;
    }

    public String getProfilePicPath() {
        return profilePicPath;
    }

    public void setProfilePicPath(String profilePicPath) {
        this.profilePicPath = profilePicPath;
    }

    public String getReviewUTCDate() {
        return reviewUTCDate;
    }

    public void setReviewUTCDate(String reviewUTCDate) {
        this.reviewUTCDate = reviewUTCDate;
    }

    public String getReviews() {
        return reviews;
    }

    public void setReviews(String reviews) {
        this.reviews = reviews;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
