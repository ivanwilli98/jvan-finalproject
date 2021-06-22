package com.ivan.final_project.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ivan.final_project.R;
import com.ivan.final_project.models.Ticket;
import com.ivan.final_project.models.TripSchedule;
import com.ivan.final_project.models.UserRegister;
import com.ivan.final_project.models.UserTicket;
import com.ivan.final_project.rest.ApiClient;
import com.ivan.final_project.rest.ApiInterface;
import com.ivan.final_project.util.MySession;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TripScheduleDetailActivity extends AppCompatActivity {

    ApiInterface apiInterface;
    private Integer idTripSchedule, passengerId;
    private String namaUser, alamatEmail, noTelp;
    private String tripDate;
    private TextView tNamaBus, tKodeBus, tFrom, tTo, tTanggalBerangkat, tFare, tKursiSisa, tNamaLengkap, tAlamatEmail, tNoTelp;
    private Button bPesanTiket;
    private MySession session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_schedule_detail);

        tNamaBus = findViewById(R.id.txtNamaBus);
        tKodeBus = findViewById(R.id.txtKodeBus);
        tFrom = findViewById(R.id.txtFrom);
        tTo = findViewById(R.id.txtTo);
        tTanggalBerangkat = findViewById(R.id.txtTripDate);
        tFare = findViewById(R.id.txtFare);
        tKursiSisa = findViewById(R.id.txtAvailableSeats);
        tNamaLengkap = findViewById(R.id.tvNamaLengkap);
        tAlamatEmail = findViewById(R.id.tvAlamatEmail);
        tNoTelp = findViewById(R.id.tvNoTelepon);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        bPesanTiket = findViewById(R.id.btnPesanTiket);

        session = new MySession(TripScheduleDetailActivity.this);

        //Check Login
        if (session.isLoggedIn()) {
            //Get Session
            HashMap<String, String> sUsernya = session.getUserDetails();
            passengerId = Integer.valueOf(sUsernya.get(MySession.KEY_ID));
            namaUser = sUsernya.get(MySession.KEY_FIRST_NAME)+" "+sUsernya.get(MySession.KEY_LAST_NAME);
            alamatEmail = sUsernya.get(MySession.KEY_EMAIL);
            noTelp = sUsernya.get(MySession.KEY_MOBILE_NUMBER);
        }

        bPesanTiket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserTicket userTicket = new UserTicket(false,tripDate,passengerId,1, idTripSchedule);
                Call<Ticket> listCall = apiInterface.postTicket(userTicket);
                listCall.enqueue(new Callback<Ticket>() {
                    @Override
                    public void onResponse(Call<Ticket> call, Response<Ticket> response) {
                        Ticket ticket = response.body();
                        Log.d("PRINT", "ticket : "+ticket.toString());
                        if (ticket != null){
                            final AlertDialog.Builder builder = new AlertDialog.Builder(TripScheduleDetailActivity.this);
                            builder.setTitle("Berhasil");
                            builder.setMessage("Berhasil pesan ticket");
                            builder.setPositiveButton("Ok",(dialog, which) -> {
                                Intent home=new Intent(TripScheduleDetailActivity.this, MainActivity.class);
                                home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(home);
                                finish();
                            });

                            builder.setCancelable(false);
                            builder.show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Ticket> call, Throwable t) {

                    }
                });
            }
        });

        refresh();
    }

    private void refresh() {
        Bundle bundle = getIntent().getExtras();
        idTripSchedule = bundle.getInt("idTripSchedule");
        Log.d("PRINT", "id: "+idTripSchedule);

        Call<TripSchedule> listCall = apiInterface.getTripScheduleById(idTripSchedule);
        listCall.enqueue(new Callback<TripSchedule>() {
            @Override
            public void onResponse(Call<TripSchedule> call, Response<TripSchedule> response) {
                TripSchedule tripSchedule = response.body();

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date newDate = format.parse(String.valueOf(tripSchedule.getTripDate()));
                    tripDate = tripSchedule.getTripDate();
                    format = new SimpleDateFormat("dd MMMM yyyy");
                    String date = format.format(newDate);
                    tTanggalBerangkat.setText(date);
                } catch (ParseException e) {
                    tTanggalBerangkat.setText(tripSchedule.getTripDate());
                }

                tNamaBus.setText(tripSchedule.getTripDetail().getBus().getAgency().getDetail());
                tKodeBus.setText(tripSchedule.getTripDetail().getBus().getCode());
                tFrom.setText(tripSchedule.getTripDetail().getSourceStop().getName());
                tTo.setText(tripSchedule.getTripDetail().getDestStop().getName());
                tKursiSisa.setText("Tersisa "+String.valueOf(tripSchedule.getAvailableSeats())+" kursi");
                tFare.setText("Rp. "+String.valueOf(tripSchedule.getTripDetail().getFare())+"/orang");
                tNamaLengkap.setText(namaUser);
                tAlamatEmail.setText(alamatEmail);
                tNoTelp.setText(noTelp);
            }

            @Override
            public void onFailure(Call<TripSchedule> call, Throwable t) {

            }
        });
    }
}