package com.ivan.final_project.models;

import com.google.gson.annotations.SerializedName;

public class Stop {
    @SerializedName("id")
    private Integer id;

    @SerializedName("code")
    private String code;

    @SerializedName("name")
    private String name;

    @SerializedName("detail")
    private String detail;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Stop() {
    }

    public Stop(Integer id, String code, String name, String detail) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.detail = detail;
    }
}
