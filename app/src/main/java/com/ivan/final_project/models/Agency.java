package com.ivan.final_project.models;

import com.google.gson.annotations.SerializedName;

public class Agency {
    @SerializedName("code")
    private String code;

    @SerializedName("detail")
    private String detail;

    @SerializedName("id")
    private Integer id;

    @SerializedName("name")
    private String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
