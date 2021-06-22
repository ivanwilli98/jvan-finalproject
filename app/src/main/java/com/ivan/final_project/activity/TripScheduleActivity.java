package com.ivan.final_project.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.ivan.final_project.R;
import com.ivan.final_project.adapter.TripScheduleListAdapter;
import com.ivan.final_project.models.TripSchedule;
import com.ivan.final_project.rest.ApiClient;
import com.ivan.final_project.rest.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Query;

public class TripScheduleActivity extends AppCompatActivity {

    RecyclerView rvListTripSchedule;
    TextView tInternetHilang, tTripScheduleKosong;
    ApiInterface apiInterface;
    private RecyclerView.Adapter mTripScheduleAdapter;
    private RecyclerView.LayoutManager mLayoutManagerTripSchedule;
    private String from, to;
    private Integer destStopId, sourceStopId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_schedule);

        setTitle("List Tiket");

        tInternetHilang = findViewById(R.id.internet_hilang);
        tTripScheduleKosong = findViewById(R.id.trip_schedule_kosong);

        rvListTripSchedule = findViewById(R.id.listTripSchedule);
        mLayoutManagerTripSchedule = new LinearLayoutManager(TripScheduleActivity.this);
        rvListTripSchedule.setLayoutManager(mLayoutManagerTripSchedule);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        refresh();
    }

    private void refresh() {
        Bundle bundle = getIntent().getExtras();
        from = bundle.getString("from").toString();
        to = bundle.getString("to").toString();
        sourceStopId = bundle.getInt("sourceStopId");
        destStopId = bundle.getInt("destStopId");
        Log.d("PRINT", "from : "+from+", to : "+to+", sourceStopId : "+sourceStopId+", destStopId : "+destStopId);

        Call<List<TripSchedule>> listCall = apiInterface.getTripSchedule(destStopId,from,sourceStopId,to);
        listCall.enqueue(new Callback<List<TripSchedule>>() {
            @Override
            public void onResponse(Call<List<TripSchedule>> call, Response<List<TripSchedule>> response) {
                List<TripSchedule> tripSchedules = response.body();
                if(!tripSchedules.isEmpty()) {
                    Log.d("PRINT", "tripSchedules : "+tripSchedules);
                    mTripScheduleAdapter = new TripScheduleListAdapter(tripSchedules,TripScheduleActivity.this);
                    rvListTripSchedule.setAdapter(mTripScheduleAdapter);
                }
                else {
                    tTripScheduleKosong.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<TripSchedule>> call, Throwable t) {
                tInternetHilang.setVisibility(View.VISIBLE);

            }
        });
    }
}