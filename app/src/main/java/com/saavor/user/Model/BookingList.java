package com.saavor.user.Model;

/**
 * Created by a123456 on 03/01/18.
 */

public class BookingList {

    private String BookingID;
    private String BookingStatus;
    private String BookingsId;
    private String BusinessName;
    private String ChefName;
    private String CustomerAddress;
    private String EventTitle;
    private String IsReOrder;
    private String IsReview;
    private String SlotDates;
    private String StarRating;
    private String Status;
    private String TipType;
    private String IsClockOutConfirm;

    public String getIsClockOutConfirm() {
        return IsClockOutConfirm;
    }

    public void setIsClockOutConfirm(String isClockOutConfirm) {
        IsClockOutConfirm = isClockOutConfirm;
    }

    public String getIsClockInConfirm() {
        return IsClockInConfirm;
    }

    public void setIsClockInConfirm(String isClockInConfirm) {
        IsClockInConfirm = isClockInConfirm;
    }

    public String getIsArrived() {
        return IsArrived;
    }

    public void setIsArrived(String isArrived) {
        IsArrived = isArrived;
    }

    private String IsClockInConfirm;
    private String IsArrived;

    public String getIsClockIn() {
        return IsClockIn;
    }

    public void setIsClockIn(String isClockIn) {
        IsClockIn = isClockIn;
    }

    public String getIsClockOut() {
        return IsClockOut;
    }

    public void setIsClockOut(String isClockOut) {
        IsClockOut = isClockOut;
    }

    private String TotalAmount;
    private String IsClockIn;
    private String IsClockOut;

    public String getBookingID() {
        return BookingID;
    }

    public void setBookingID(String bookingID) {
        BookingID = bookingID;
    }

    public String getBookingStatus() {
        return BookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        BookingStatus = bookingStatus;
    }

    public String getBookingsId() {
        return BookingsId;
    }

    public void setBookingsId(String bookingsId) {
        BookingsId = bookingsId;
    }

    public String getBusinessName() {
        return BusinessName;
    }

    public void setBusinessName(String businessName) {
        BusinessName = businessName;
    }

    public String getChefName() {
        return ChefName;
    }

    public void setChefName(String chefName) {
        ChefName = chefName;
    }

    public String getCustomerAddress() {
        return CustomerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        CustomerAddress = customerAddress;
    }

    public String getEventTitle() {
        return EventTitle;
    }

    public void setEventTitle(String eventTitle) {
        EventTitle = eventTitle;
    }

    public String getIsReOrder() {
        return IsReOrder;
    }

    public void setIsReOrder(String isReOrder) {
        IsReOrder = isReOrder;
    }

    public String getIsReview() {
        return IsReview;
    }

    public void setIsReview(String isReview) {
        IsReview = isReview;
    }

    public String getSlotDates() {
        return SlotDates;
    }

    public void setSlotDates(String slotDates) {
        SlotDates = slotDates;
    }

    public String getStarRating() {
        return StarRating;
    }

    public void setStarRating(String starRating) {
        StarRating = starRating;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getTipType() {
        return TipType;
    }

    public void setTipType(String tipType) {
        TipType = tipType;
    }

    public String getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        TotalAmount = totalAmount;
    }
}
