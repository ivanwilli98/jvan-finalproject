package com.ivan.final_project.models;

import com.google.gson.annotations.SerializedName;

public class UserTicket {
    @SerializedName("cancellable")
    private Boolean cancellable;

    @SerializedName("journeyDate")
    private String journeyDate;

    @SerializedName("passengerId")
    private Integer passengerId;

    @SerializedName("seatNumber")
    private Integer seatNumber;

    @SerializedName("tripScheduleId")
    private Integer tripScheduleId;

    public UserTicket() {
    }

    public UserTicket(Boolean cancellable, String journeyDate, Integer passengerId, Integer seatNumber, Integer tripScheduleId) {
        this.cancellable = cancellable;
        this.journeyDate = journeyDate;
        this.passengerId = passengerId;
        this.seatNumber = seatNumber;
        this.tripScheduleId = tripScheduleId;
    }

    public Boolean getCancellable() {
        return cancellable;
    }

    public void setCancellable(Boolean cancellable) {
        this.cancellable = cancellable;
    }

    public String getJourneyDate() {
        return journeyDate;
    }

    public void setJourneyDate(String journeyDate) {
        this.journeyDate = journeyDate;
    }

    public Integer getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(Integer passengerId) {
        this.passengerId = passengerId;
    }

    public Integer getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(Integer seatNumber) {
        this.seatNumber = seatNumber;
    }

    public Integer getTripScheduleId() {
        return tripScheduleId;
    }

    public void setTripScheduleId(Integer tripScheduleId) {
        this.tripScheduleId = tripScheduleId;
    }
}
