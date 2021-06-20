package com.ivan.final_project.models;

import com.google.gson.annotations.SerializedName;

public class Role {
    @SerializedName("id")
    private Integer id;

    @SerializedName("role")
    private String role;

    public Role() { }

    public Role(String role) {
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
