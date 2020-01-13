package com.saavor.user.Model;

/**
 * Created by a123456 on 31/01/18.
 */

public class BookingStatusUpdate {

    private String BookingsId;
    private String CurrentDate;
    private String IsClockInConfirm;
    private String IsClockOutConfirm;
    private String SessionToken;
    private String UserId;

    public String getBookingsId() {
        return BookingsId;
    }

    public void setBookingsId(String bookingsId) {
        BookingsId = bookingsId;
    }

    public String getCurrentDate() {
        return CurrentDate;
    }

    public void setCurrentDate(String currentDate) {
        CurrentDate = currentDate;
    }

    public String getIsClockInConfirm() {
        return IsClockInConfirm;
    }

    public void setIsClockInConfirm(String isClockInConfirm) {
        IsClockInConfirm = isClockInConfirm;
    }

    public String getIsClockOutConfirm() {
        return IsClockOutConfirm;
    }

    public void setIsClockOutConfirm(String isClockOutConfirm) {
        IsClockOutConfirm = isClockOutConfirm;
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
}
