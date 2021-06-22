package com.ivan.final_project.models;

import com.google.gson.annotations.SerializedName;

public class TripScheduleBody {
    @SerializedName("destStopId")
    private Integer destStopId;

    @SerializedName("from")
    private String from;

    @SerializedName("sourceStopId")
    private Integer sourceStopId;

    @SerializedName("to")
    private String to;

    public TripScheduleBody() {
    }

    public TripScheduleBody(Integer destStopId, String from, Integer sourceStopId, String to) {
        this.destStopId = destStopId;
        this.from = from;
        this.sourceStopId = sourceStopId;
        this.to = to;
    }

    public Integer getDestStopId() {
        return destStopId;
    }

    public void setDestStopId(Integer destStopId) {
        this.destStopId = destStopId;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public Integer getSourceStopId() {
        return sourceStopId;
    }

    public void setSourceStopId(Integer sourceStopId) {
        this.sourceStopId = sourceStopId;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
