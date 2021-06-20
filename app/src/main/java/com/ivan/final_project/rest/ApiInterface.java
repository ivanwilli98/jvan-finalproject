package com.ivan.final_project.rest;

import com.ivan.final_project.models.ResponseUser;
import com.ivan.final_project.models.User;
import com.ivan.final_project.models.UserRegister;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {
    @POST("auth/register")
    Call<User> createUser(@Body UserRegister register);

    @POST("auth/login")
    Call<ResponseUser> getLogin(@Body RequestBody params);
}
