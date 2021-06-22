package com.ivan.final_project.models;

import com.google.gson.annotations.SerializedName;

public class Ticket {
    @SerializedName("cancellable")
    private Boolean cancellable;

    @SerializedName("id")
    private Integer id;

    @SerializedName("journeyDate")
    private String journeyDate;

    @SerializedName("passenger")
    private User passenger;

    @SerializedName("seatNumber")
    private Integer seatNumber;

    @SerializedName("tripSchedule")
    private TripSchedule tripSchedule;

    public Ticket() {
    }

    public Ticket(Boolean cancellable, Integer id, String journeyDate, User passenger, Integer seatNumber, TripSchedule tripSchedule) {
        this.cancellable = cancellable;
        this.id = id;
        this.journeyDate = journeyDate;
        this.passenger = passenger;
        this.seatNumber = seatNumber;
        this.tripSchedule = tripSchedule;
    }

    public Boolean getCancellable() {
        return cancellable;
    }

    public void setCancellable(Boolean cancellable) {
        this.cancellable = cancellable;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJourneyDate() {
        return journeyDate;
    }

    public void setJourneyDate(String journeyDate) {
        this.journeyDate = journeyDate;
    }

    public User getPassenger() {
        return passenger;
    }

    public void setPassenger(User passenger) {
        this.passenger = passenger;
    }

    public Integer getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(Integer seatNumber) {
        this.seatNumber = seatNumber;
    }

    public TripSchedule getTripSchedule() {
        return tripSchedule;
    }

    public void setTripSchedule(TripSchedule tripSchedule) {
        this.tripSchedule = tripSchedule;
    }
}
