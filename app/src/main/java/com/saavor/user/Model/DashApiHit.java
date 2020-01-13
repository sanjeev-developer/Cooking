package com.saavor.user.Model;

/**
 * Created by a123456 on 26/06/17.
 */

public class DashApiHit {

    private String CurrentDate;
    private String Distance;
    private String SessionToken;
    private String UserId;
    private String Latitude;
    private String Longitude;
    private String StartIndex;
    private String EndIndex;

    public String getStartIndex() {
        return StartIndex;
    }

    public void setStartIndex(String startIndex) {
        StartIndex = startIndex;
    }

    public String getEndIndex() {
        return EndIndex;
    }

    public void setEndIndex(String endIndex) {
        EndIndex = endIndex;
    }

    public String getCurrentDate() {
        return CurrentDate;
    }

    public void setCurrentDate(String currentDate) {
        CurrentDate = currentDate;
    }

    public String getDistance() {
        return Distance;
    }

    public void setDistance(String distance) {
        Distance = distance;
    }

    public String getSessionToken() {
        return SessionToken;
    }

    public void setSessionToken(String sessionToken) {
        SessionToken = sessionToken;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }
}
