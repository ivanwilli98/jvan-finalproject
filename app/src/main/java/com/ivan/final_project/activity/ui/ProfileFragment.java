package com.ivan.final_project.activity.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ivan.final_project.R;
import com.ivan.final_project.activity.ComingSoonActivity;
import com.ivan.final_project.activity.EditProfileActivity;
import com.ivan.final_project.activity.LoginActivity;
import com.ivan.final_project.activity.MainActivity;
import com.ivan.final_project.activity.UbahPasswordActivity;
import com.ivan.final_project.util.MySession;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static MySession session;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String firstName, lastName, email;
    private TextView tFirstName, tLastName, tEmail;
    private TextView tEditProfile, tUbahPassword, tPusatBantuan, tTentangKami;

    public ProfileFragment() {
        // Required empty public constructor
    }

    private Button bLogout;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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

        //Check Login
        if (session.isLoggedIn()) {
            //Get Session
            HashMap<String, String> sUsernya = session.getUserDetails();
            firstName = sUsernya.get(MySession.KEY_FIRST_NAME);
            lastName = sUsernya.get(MySession.KEY_LAST_NAME);
            email = sUsernya.get(MySession.KEY_EMAIL);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootview = inflater.inflate(R.layout.fragment_profile, container, false);

        bLogout = rootview.findViewById(R.id.btnLogout);
        tFirstName = rootview.findViewById(R.id.txtFirstname);
        tLastName = rootview.findViewById(R.id.txtLastname);
        tEmail = rootview.findViewById(R.id.txtEmail);
        tEditProfile = rootview.findViewById(R.id.tvEditProfile);
        tUbahPassword = rootview.findViewById(R.id.tvUbahPassword);
        tPusatBantuan = rootview.findViewById(R.id.tvPusatBantuan);
        tTentangKami = rootview.findViewById(R.id.tvTentangKami);

        tFirstName.setText(firstName+" ");
        tLastName.setText(lastName+" ");
        tEmail.setText(email+" ");

        bLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        tEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), EditProfileActivity.class);
                startActivity(i);
            }
        });

        tUbahPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), UbahPasswordActivity.class);
                startActivity(i);
            }
        });

        tPusatBantuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ComingSoonActivity.class);
                startActivity(i);
            }
        });

        tTentangKami.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ComingSoonActivity.class);
                startActivity(i);
            }
        });

        return rootview;
    }

    public void logout() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Logout");
        builder.setMessage("Apakah anda yakin mau logout?");
        builder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        session.logoutUser();
                        dialog.dismiss();
                        Intent i = new Intent(getActivity(), LoginActivity.class);
                        startActivity(i);
                    }
                });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setCancelable(false);
        builder.show();
    }
}