package com.ivan.final_project.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ivan.final_project.R;
import com.ivan.final_project.activity.LoginActivity;
import com.ivan.final_project.activity.MainActivity;
import com.ivan.final_project.activity.TripScheduleActivity;
import com.ivan.final_project.activity.TripScheduleDetailActivity;
import com.ivan.final_project.models.TripSchedule;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TripScheduleListAdapter extends RecyclerView.Adapter<TripScheduleListAdapter.MyViewHolder> {
    List<TripSchedule> tripScheduleList;
    private Context context;

    public TripScheduleListAdapter(List<TripSchedule> tripScheduleList, Context a) {
        this.tripScheduleList = tripScheduleList;
        this.context = a;
    }

    @NonNull
    @NotNull
    @Override
    public TripScheduleListAdapter.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tripschedule_list, parent, false);
        return new MyViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull TripScheduleListAdapter.MyViewHolder holder, int position) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date newDate = format.parse(String.valueOf(tripScheduleList.get(position).getTripDate()));
            format = new SimpleDateFormat("dd MMMM yyyy");
            String date = format.format(newDate);
            holder.tTanggalBerangkat.setText(date);
        } catch (ParseException e) {
            holder.tTanggalBerangkat.setText(tripScheduleList.get(position).getTripDate());
        }

        holder.tNamaBus.setText(tripScheduleList.get(position).getTripDetail().getBus().getAgency().getDetail());
        holder.tKodeBus.setText(tripScheduleList.get(position).getTripDetail().getBus().getCode());
        holder.tFrom.setText(tripScheduleList.get(position).getTripDetail().getSourceStop().getName());
        holder.tTo.setText(tripScheduleList.get(position).getTripDetail().getDestStop().getName());
        holder.tKursiSisa.setText("Tersisa "+String.valueOf(tripScheduleList.get(position).getAvailableSeats())+" kursi");
        holder.tFare.setText("Rp. "+String.valueOf(tripScheduleList.get(position).getTripDetail().getFare())+"/orang");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("idTripSchedule", tripScheduleList.get(position).getId());
                Intent i = new Intent(v.getContext(), TripScheduleDetailActivity.class);
                i.putExtras(bundle);
                v.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tripScheduleList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tNamaBus, tKodeBus, tFrom, tTo, tTanggalBerangkat, tKursiSisa, tFare;
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tNamaBus = itemView.findViewById(R.id.txtNamaBus);
            tKodeBus = itemView.findViewById(R.id.txtKodeBus);
            tFrom = itemView.findViewById(R.id.txtFrom);
            tTo = itemView.findViewById(R.id.txtTo);
            tTanggalBerangkat = itemView.findViewById(R.id.txtTripDate);
            tKursiSisa = itemView.findViewById(R.id.txtAvailableSeats);
            tFare = itemView.findViewById(R.id.txtFare);
        }
    }
}
