package com.ivan.final_project.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ivan.final_project.R;
import com.ivan.final_project.models.Ticket;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TicketListAdapter extends RecyclerView.Adapter<TicketListAdapter.MyViewHolder>{
    List<Ticket> ticketList;
    private Context context;

    public TicketListAdapter(List<Ticket> ticketList, Context a) {
        this.ticketList = ticketList;
        this.context = a;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.ticket_list, parent, false);
        return new TicketListAdapter.MyViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull TicketListAdapter.MyViewHolder holder, int position) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date today = Calendar.getInstance().getTime(); // get tanggal hari ini
            Date newDate = format.parse(String.valueOf(ticketList.get(position).getJourneyDate()));
            if(today.after(newDate)) {
                holder.itemView.setVisibility(View.GONE);
                ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
                params.height = 0;
                holder.itemView.setLayoutParams(params);
            } else {
                format = new SimpleDateFormat("dd MMMM yyyy");
                String date = format.format(newDate);
                holder.tTripDate.setText(date);
            }

        } catch (ParseException e) {
            holder.tTripDate.setText(ticketList.get(position).getJourneyDate());
        }

        Log.d("PRINT", "ticketList : "+ticketList.toString());
        holder.tNamaBus.setText(ticketList.get(position).getTripSchedule().getTripDetail().getAgency().getDetail());
        holder.tKodeBus.setText(ticketList.get(position).getTripSchedule().getTripDetail().getBus().getCode());
        holder.tFrom.setText(ticketList.get(position).getTripSchedule().getTripDetail().getSourceStop().getName());
        holder.tTo.setText(ticketList.get(position).getTripSchedule().getTripDetail().getDestStop().getName());
        holder.tFare.setText("Rp. "+String.valueOf(ticketList.get(position).getTripSchedule().getTripDetail().getFare()));
    }

    @Override
    public int getItemCount() {
        return ticketList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tNamaBus, tKodeBus, tFrom, tTo, tTripDate, tFare;
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tNamaBus = itemView.findViewById(R.id.txtNamaBus);
            tKodeBus = itemView.findViewById(R.id.txtKodeBus);
            tFrom = itemView.findViewById(R.id.txtFrom);
            tTo = itemView.findViewById(R.id.txtTo);
            tTripDate = itemView.findViewById(R.id.txtTripDate);
            tFare = itemView.findViewById(R.id.txtFare);
        }
    }
}
