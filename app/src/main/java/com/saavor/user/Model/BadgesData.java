package com.saavor.user.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by a123456 on 20/01/18.
 */

public class BadgesData {

    @SerializedName("BageCategoryList")
    @Expose
    private ArrayList<BageCategoryList> bageCategoryList = null;
    @SerializedName("ReturnCode")
    @Expose
    private String returnCode;
    @SerializedName("ReturnMessage")
    @Expose
    private String returnMessage;

    public ArrayList<BageCategoryList> getBageCategoryList() {
        return bageCategoryList;
    }

    public void setBageCategoryList(ArrayList<BageCategoryList> bageCategoryList) {
        this.bageCategoryList = bageCategoryList;
    }

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
}
