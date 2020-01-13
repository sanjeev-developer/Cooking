package com.saavor.user.Model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by a123456 on 12/09/17.
 */

public class ReviewReceive {

    @SerializedName("ReturnCode")
    @Expose
    private String returnCode;
    @SerializedName("ReturnMessage")
    @Expose
    private String returnMessage;
    @SerializedName("ReviewList")
    @Expose
    private ArrayList<ReviewList> reviewList = null;

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMessage() {
        return returnMessage;
    }

    public void setReturnMessage(String returnMessage) {
        this.returnMessage = returnMessage;
    }

    public ArrayList<ReviewList> getReviewList() {
        return reviewList;
    }

    public void setReviewList(ArrayList<ReviewList> reviewList) {
        this.reviewList = reviewList;
    }
}
