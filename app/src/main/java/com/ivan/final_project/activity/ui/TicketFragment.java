package com.ivan.final_project.activity.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ivan.final_project.R;
import com.ivan.final_project.adapter.TicketListAdapter;
import com.ivan.final_project.models.Ticket;
import com.ivan.final_project.rest.ApiClient;
import com.ivan.final_project.rest.ApiInterface;
import com.ivan.final_project.util.MySession;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TicketFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TicketFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //tambah disini
    private View fragmentView;
    private MySession session;
    RecyclerView rvListTicket;
    TextView tInternetHilang, tTicketKosong;
    ApiInterface apiInterface;
    private RecyclerView.Adapter mTicketAdapter;
    private RecyclerView.LayoutManager mLayoutManagerTicket;
    private String from, to, tripDate, namaBus, kodeBus;
    private Integer passengerId;
    private TextView tNamabus, tKodeBus, tFrom, tTo, tTripDate, tFare;

    public TicketFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BusFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TicketFragment newInstance(String param1, String param2) {
        TicketFragment fragment = new TicketFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new MySession(getActivity());
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentView = inflater.inflate(R.layout.fragment_ticket, container, false);

        tNamabus = fragmentView.findViewById(R.id.txtNamaBus);
        tKodeBus = fragmentView.findViewById(R.id.txtKodeBus);
        tFrom = fragmentView.findViewById(R.id.txtFrom);
        tTo = fragmentView.findViewById(R.id.txtTo);
        tTripDate = fragmentView.findViewById(R.id.txtTripDate);
        tFare = fragmentView.findViewById(R.id.txtFare);

        rvListTicket = fragmentView.findViewById(R.id.listTicket);
        mLayoutManagerTicket = new LinearLayoutManager(getActivity());
        rvListTicket.setLayoutManager(mLayoutManagerTicket);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        tInternetHilang = fragmentView.findViewById(R.id.internet_hilang);
        tTicketKosong = fragmentView.findViewById(R.id.ticket_kosong);

        refresh();
        return fragmentView;
    }

    private void refresh() {
        //Check Login
        if (session.isLoggedIn()) {
            //Get Session
            HashMap<String, String> sUsernya = session.getUserDetails();
            passengerId = Integer.valueOf(sUsernya.get(MySession.KEY_ID));
            Log.d("PRINT", "passengerId : "+passengerId);
        }

        Call<List<Ticket>> listCall = apiInterface.getTicketUser(passengerId);
        Log.d("PRINT", "listCall : "+listCall.toString());
        listCall.enqueue(new Callback<List<Ticket>>() {
            @Override
            public void onResponse(Call<List<Ticket>> call, Response<List<Ticket>> response) {
                List<Ticket> tickets = response.body();
                if(!tickets.isEmpty()) {
                    mTicketAdapter = new TicketListAdapter(tickets,getActivity());
                    rvListTicket.setAdapter(mTicketAdapter);
                }
                else {
                    tTicketKosong.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<Ticket>> call, Throwable t) {
                tInternetHilang.setVisibility(View.VISIBLE);
            }
        });
    }
}