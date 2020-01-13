package com.saavor.user.Model;

import java.util.ArrayList;

/**
 * Created by a123456 on 03/05/17.
 */

public class RefrenceSend {

    private String ProfileId;
    private String CreateDate;
    private String KitchenId;
    private String KitchenReferences;

    public String getKitchenReferences() {
        return KitchenReferences;
    }

    public void setKitchenReferences(String kitchenReferences) {
        KitchenReferences = kitchenReferences;
    }

    ArrayList<References>objRefrence=new ArrayList<References>();

    public String getProfileId() {
        return ProfileId;
    }

    public void setProfileId(String profileId) {
        ProfileId = profileId;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public String getKitchenId() {
        return KitchenId;
    }

    public void setKitchenId(String kitchenId) {
        KitchenId = kitchenId;
    }

}
