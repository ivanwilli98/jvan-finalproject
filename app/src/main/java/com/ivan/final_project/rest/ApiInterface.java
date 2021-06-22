package com.ivan.final_project.rest;

import com.ivan.final_project.models.ChangePassword;
import com.ivan.final_project.models.ResponseUser;
import com.ivan.final_project.models.Stop;
import com.ivan.final_project.models.Ticket;
import com.ivan.final_project.models.TripSchedule;
import com.ivan.final_project.models.User;
import com.ivan.final_project.models.UserRegister;
import com.ivan.final_project.models.UserTicket;
import com.ivan.final_project.models.UserUpdate;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    @POST("auth/register")
    Call<User> createUser(@Body UserRegister register);

    @POST("auth/login")
    Call<ResponseUser> getLogin(@Body RequestBody params);

    @GET("stop")
    Call<List<Stop>> getStop();

    @GET("tripSchedule")
    Call<List<TripSchedule>> getTripSchedule(@Query("destStopId") Integer destStopId, @Query("from") String from, @Query("sourceStopId") Integer sourceStopId, @Query("to") String to);

    @GET("tripSchedule/{id}")
    Call<TripSchedule> getTripScheduleById(@Path("id") Integer id);

    @POST("ticket")
    Call<Ticket> postTicket(@Body UserTicket ticket);

    @GET("ticket")
    Call<List<Ticket>> getTicketUser(@Query("passengerId") Integer passengerId);

    @POST("user/update")
    Call<User> postUpdateUser(@Body UserUpdate userUpdate);

    @POST("user/changePassword")
    Call<User> postChangePassword(@Body ChangePassword changePassword);
}
