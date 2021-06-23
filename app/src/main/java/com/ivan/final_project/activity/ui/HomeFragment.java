package com.ivan.final_project.activity.ui;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.collection.ArrayMap;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.ivan.final_project.R;
import com.ivan.final_project.activity.ComingSoonActivity;
import com.ivan.final_project.activity.TripScheduleActivity;
import com.ivan.final_project.models.Stop;
import com.ivan.final_project.models.TripSchedule;
import com.ivan.final_project.rest.ApiClient;
import com.ivan.final_project.rest.ApiInterface;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // tambah disini
    private EditText edCalendar, edStartDate, edEndDate;
    private Button bSubmit;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;
    private Spinner spFrom, spTo;
    private ApiInterface apiInterface;
    private List<Integer> spinnerListId;
    private List<String> spinnerListName;
    private View fragmentView;
    private Integer FromHolder;
    private Integer ToHolder;
    private String StartDateHolder, EndDateHolder;
    private Boolean CheckEditText;
    private View vPengumuman;
    private LinearLayout linearLayoutPromoSaatIni, linearLayoutTesCovid;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentView = inflater.inflate(R.layout.fragment_home, container, false);

        myCalendar = Calendar.getInstance();

        edStartDate = fragmentView.findViewById(R.id.edStartDate);
        edEndDate = fragmentView.findViewById(R.id.edEndDate);
        spFrom = fragmentView.findViewById(R.id.spFrom);
        spTo = fragmentView.findViewById(R.id.spTo);
        bSubmit = fragmentView.findViewById(R.id.btnSubmit);
        vPengumuman = fragmentView.findViewById(R.id.viewPengumuman);
        linearLayoutPromoSaatIni = fragmentView.findViewById(R.id.llPromoSaatIni);
        linearLayoutTesCovid = fragmentView.findViewById(R.id.llTestCovid);

        date = (view, year, monthOfYear, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel(edCalendar);
        };

        edStartDate.setOnClickListener(v -> {
            showCalendar(date);
            edCalendar = edStartDate;
        });

        edEndDate.setOnClickListener(v -> {
            showCalendar(date);
            edCalendar = edEndDate;
        });

        bSubmit.setOnClickListener(view -> cekSubmit());

        vPengumuman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ComingSoonActivity.class);
                startActivity(i);
            }
        });

        linearLayoutPromoSaatIni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ComingSoonActivity.class);
                startActivity(i);
            }
        });

        linearLayoutTesCovid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ComingSoonActivity.class);
                startActivity(i);
            }
        });

        getStop();

        return fragmentView;
    }

    private void cekSubmit() {
        CheckEditTextIsEmptyOrNot();
        if(CheckEditText) {
//            SyncCek();
            Bundle bundle = new Bundle();
            bundle.putString("from", StartDateHolder);
            bundle.putString("to", EndDateHolder);
            bundle.putInt("sourceStopId", FromHolder);
            bundle.putInt("destStopId", ToHolder);

            Intent i = new Intent(getActivity(), TripScheduleActivity.class);
            i.putExtras(bundle);
            startActivity(i);
            ((Activity) getActivity()).overridePendingTransition(0, 0);
        }
        else {
            Toast.makeText(getActivity(), "Data masih kosong!", Toast.LENGTH_LONG).show();
        }
    }

    private void CheckEditTextIsEmptyOrNot() {
//        spFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String item_position = String.valueOf(position);
//                FromHolder = Integer.valueOf(item_position)+1;
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });
//        spTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String item_position = String.valueOf(position);
//                ToHolder = Integer.valueOf(item_position)+1;
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });
        int idxfrom = spFrom.getSelectedItemPosition();
        FromHolder = spinnerListId.get(idxfrom);

        int idxTo = spTo.getSelectedItemPosition();
        ToHolder = spinnerListId.get(idxTo);

        StartDateHolder = edStartDate.getText().toString();
        EndDateHolder = edEndDate.getText().toString();
//        StartDateHolder = "2020-01-01";
//        EndDateHolder = "2020-01-01";

        if(spFrom.equals(null) || spTo.equals(null) || TextUtils.isEmpty(StartDateHolder) || TextUtils.isEmpty(EndDateHolder)) {
            CheckEditText = false;
        }
        else {
            CheckEditText = true;
        }
    }

    private void showCalendar(DatePickerDialog.OnDateSetListener date) {
        DatePickerDialog dialog = new DatePickerDialog(
                getActivity(),
                date,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));
        dialog.getDatePicker().setMinDate(new Date().getTime());
        dialog.show();
    }

    private void updateLabel(EditText editText) {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editText.setText(sdf.format(myCalendar.getTime()));
    }

    private void getStop() {
        spinnerListId = new ArrayList<>();
        spinnerListName = new ArrayList<>();
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Stop>> stopCall = apiInterface.getStop();

        stopCall.enqueue(new Callback<List<Stop>>() {
            @Override
            public void onResponse(Call<List<Stop>> call, Response<List<Stop>> response) {
                List<Stop> stops = response.body();
                for (int i = 0; i < stops.size(); i++) {
                    spinnerListId.add(stops.get(i).getId());
                    spinnerListName.add(stops.get(i).getName());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(fragmentView.getContext(), R.layout.spinner_list_item, spinnerListName);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spFrom.setAdapter(adapter);
                spTo.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Stop>> call, Throwable t) {

            }
        });
    }
}