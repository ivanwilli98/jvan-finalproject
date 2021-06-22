package com.ivan.final_project.models;

import com.google.gson.annotations.SerializedName;

public class TripDetail {
    @SerializedName("agency")
    private Agency agency;

    @SerializedName("bus")
    private Bus bus;

    @SerializedName("destStop")
    private Stop destStop;

    @SerializedName("fare")
    private Integer fare;

    @SerializedName("id")
    private Integer id;

    @SerializedName("journeyTime")
    private String journeyTime;

    @SerializedName("sourceStop")
    private Stop sourceStop;

    public TripDetail() {
    }

    public TripDetail(Agency agency, Bus bus, Stop destStop, Integer fare, Integer id, String journeyTime, Stop sourceStop) {
        this.agency = agency;
        this.bus = bus;
        this.destStop = destStop;
        this.fare = fare;
        this.id = id;
        this.journeyTime = journeyTime;
        this.sourceStop = sourceStop;
    }

    public Agency getAgency() {
        return agency;
    }

    public void setAgency(Agency agency) {
        this.agency = agency;
    }

    public Bus getBus() {
        return bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }

    public Stop getDestStop() {
        return destStop;
    }

    public void setDestStop(Stop destStop) {
        this.destStop = destStop;
    }

    public Integer getFare() {
        return fare;
    }

    public void setFare(Integer fare) {
        this.fare = fare;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJourneyTime() {
        return journeyTime;
    }

    public void setJourneyTime(String journeyTime) {
        this.journeyTime = journeyTime;
    }

    public Stop getSourceStop() {
        return sourceStop;
    }

    public void setSourceStop(Stop sourceStop) {
        this.sourceStop = sourceStop;
    }
}
