package com.ivan.final_project.models;

import com.google.gson.annotations.SerializedName;

public class Bus {
    @SerializedName("agency")
    private Agency agency;

    @SerializedName("capacity")
    private Integer capacity;

    @SerializedName("code")
    private String code;

    @SerializedName("id")
    private Integer id;

    @SerializedName("make")
    private String make;

    public Agency getAgency() {
        return agency;
    }

    public void setAgency(Agency agency) {
        this.agency = agency;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }
}
