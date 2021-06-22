package com.ivan.final_project.models;

import com.google.gson.annotations.SerializedName;

public class TripSchedule {
    @SerializedName("availableSeats")
    private Integer availableSeats;

    @SerializedName("id")
    private Integer id;

    @SerializedName("tripDate")
    private String tripDate;

    @SerializedName("tripDetail")
    private TripDetail tripDetail;

    public TripSchedule() {
    }

    public TripSchedule(Integer availableSeats, Integer id, String tripDate, TripDetail tripDetail) {
        this.availableSeats = availableSeats;
        this.id = id;
        this.tripDate = tripDate;
        this.tripDetail = tripDetail;
    }

    public Integer getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(Integer availableSeats) {
        this.availableSeats = availableSeats;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTripDate() {
        return tripDate;
    }

    public void setTripDate(String tripDate) {
        this.tripDate = tripDate;
    }

    public TripDetail getTripDetail() {
        return tripDetail;
    }

    public void setTripDetail(TripDetail tripDetail) {
        this.tripDetail = tripDetail;
    }
}
