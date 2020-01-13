package com.saavor.user.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by a123456 on 20/01/18.
 */

public class BageCategoryList {

    @SerializedName("BadgeList")
    @Expose
    public ArrayList<BadgeList> badgeList = null;
    @SerializedName("CategoryName")
    @Expose
    public String categoryName;
    @SerializedName("Description")
    @Expose
    public String description;

    public ArrayList<BadgeList> getBadgeList() {
        return badgeList;
    }

    public void setBadgeList(ArrayList<BadgeList> badgeList) {
        this.badgeList = badgeList;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
