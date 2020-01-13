
package com.saavor.user.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BookmarkList {

    @SerializedName("ChefBookmarkList")
    @Expose
    private ArrayList<ChefBookmarkList> chefBookmarkList = null;
    @SerializedName("KitchenBookmarkList")
    @Expose
    private ArrayList<KitchenBookmarkList> kitchenBookmarkList = null;
    @SerializedName("ReturnCode")
    @Expose
    private String returnCode;
    @SerializedName("ReturnMessage")
    @Expose
    private String returnMessage;

    public ArrayList<ChefBookmarkList> getChefBookmarkList() {
        return chefBookmarkList;
    }

    public void setChefBookmarkList(ArrayList<ChefBookmarkList> chefBookmarkList) {
        this.chefBookmarkList = chefBookmarkList;
    }

    public ArrayList<KitchenBookmarkList> getKitchenBookmarkList() {
        return kitchenBookmarkList;
    }

    public void setKitchenBookmarkList(ArrayList<KitchenBookmarkList> kitchenBookmarkList) {
        this.kitchenBookmarkList = kitchenBookmarkList;
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
}}
