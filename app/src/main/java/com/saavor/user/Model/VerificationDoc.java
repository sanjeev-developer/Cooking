package com.saavor.user.Model;

/**
 * Created by a123456 on 27/04/17.
 */

public class VerificationDoc {

    private String KitchenDocs;
    private String ProfileId;
    private String CreateDate;
    private String KitchenId;

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

    public String getKitchenDocs() {
        return KitchenDocs;
    }

    public void setKitchenDocs(String kitchenDocs) {
        KitchenDocs = kitchenDocs;
    }
}
