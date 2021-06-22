package com.ivan.final_project.models;

import com.google.gson.annotations.SerializedName;

public class ChangePassword {
    @SerializedName("password")
    private String password;

    public ChangePassword() {
    }

    public ChangePassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
