package com.saavor.user.Model;

/**
 * Created by a123456 on 28/04/17.
 */

public class VerificationImg {

    private String DocFileImg;
    private String DocFilePath;
    private String DocName;
    private String KitchenDocId;

    public String getDocFileImg() {
        return DocFileImg;
    }

    public void setDocFileImg(String docFileImg) {
        DocFileImg = docFileImg;
    }

    public String getDocFilePath() {
        return DocFilePath;
    }

    public void setDocFilePath(String docFilePath) {
        DocFilePath = docFilePath;
    }

    public String getDocName() {
        return DocName;
    }

    public void setDocName(String docName) {
        DocName = docName;
    }

    public String getKitchenDocId() {
        return KitchenDocId;
    }

    public void setKitchenDocId(String kitchenDocId) {
        KitchenDocId = kitchenDocId;
    }
}
