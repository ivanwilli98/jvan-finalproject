package com.ivan.final_project.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.ivan.final_project.R;
import com.ivan.final_project.models.User;
import com.ivan.final_project.models.UserUpdate;
import com.ivan.final_project.rest.ApiClient;
import com.ivan.final_project.rest.ApiInterface;
import com.ivan.final_project.rest.RetrofitInstance;
import com.ivan.final_project.util.MySession;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {

    private static MySession session;
    private TextInputEditText tFirstName, tLastName, tEmail, tMobileNumber;
    private String sFirstName, sLastName, sEmail, sMobileNumber, sToken;
    private Button bEditProfile;
    ApiInterface apiInterface;
    private User newUserUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        session = new MySession(EditProfileActivity.this);

        tFirstName = findViewById(R.id.txtFirstname);
        tLastName = findViewById(R.id.txtLastname);
        tEmail = findViewById(R.id.txtEmail);
        tMobileNumber = findViewById(R.id.txtMobileNumber);
        bEditProfile = findViewById(R.id.btnEditProfile);

        if (session.isLoggedIn()) {
            //Get Session
            HashMap<String, String> sUsernya = session.getUserDetails();
            sFirstName = sUsernya.get(MySession.KEY_FIRST_NAME);
            sLastName = sUsernya.get(MySession.KEY_LAST_NAME);
            sEmail = sUsernya.get(MySession.KEY_EMAIL);
            sMobileNumber = sUsernya.get(MySession.KEY_MOBILE_NUMBER);
            sToken = sUsernya.get(MySession.KEY_TOKEN);
            Log.d("PRINT", "token : "+sToken);
//            apiInterface = ApiClient.getClient(sToken).create(ApiInterface.class);
            apiInterface = RetrofitInstance.getRetrofitInstance(""+sToken).create(ApiInterface.class);
        }

        tFirstName.setText(sFirstName);
        tLastName.setText(sLastName);
        tEmail.setText(sEmail);
        disableEditText(tEmail);
        tMobileNumber.setText(sMobileNumber);

        bEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tFirstName.getText().toString().length()==0 || tLastName.getText().toString().length()==0 || tMobileNumber.getText().toString().length()==0) {
                    if(tFirstName.getText().toString().length()==0 ) {
                        tFirstName.setError("Firstname diperlukan!");
                    }
                    if(tLastName.getText().toString().length()==0) {
                        tLastName.setError("Lastname diperlukan!");
                    }
                    if(tMobileNumber.getText().toString().length()==0) {
                        tMobileNumber.setError("Mobile number diperlukan!");
                    }
                }
                else {
                    UserUpdate userUpdate = new UserUpdate(
                            tFirstName.getText().toString(),
                            tLastName.getText().toString(),
                            tMobileNumber.getText().toString()
                    );
                    Call<User> userCall = apiInterface.postUpdateUser(userUpdate);
                    userCall.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            Log.d("PRINT", "response : "+response.code());
                            newUserUpdate = response.body();
                            Log.d("PRINT", "newUserUpdate : "+newUserUpdate);
                            if(newUserUpdate != null) {
                                updateSession();
                                final AlertDialog.Builder builder = new AlertDialog.Builder(EditProfileActivity.this);
                                builder.setTitle("information");
                                builder.setMessage("Update Profile Berhasil Dilakukan");
                                builder.setPositiveButton("Ok",(dialog, which) -> {
                                    finish();
                                });
                                builder.setCancelable(false);
                                builder.show();
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Log.d("PRINT", "onFailure: " + t.toString());
                        }
                    });
                }
            }
        });
    }

    private void disableEditText(TextInputEditText editText) {
        editText.setFocusable(false);
        editText.setEnabled(false);
    }

    private void updateSession() {
        String newFirstname, newLastname, newMobileNumber;
        newFirstname = newUserUpdate.getFirstName();
        newLastname = newUserUpdate.getLastName();
        newMobileNumber = newUserUpdate.getMobileNumber();
        session.updateLoginSession(newFirstname, newLastname, newMobileNumber);
    }
}