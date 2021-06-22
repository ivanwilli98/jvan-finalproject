package com.ivan.final_project.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.ivan.final_project.R;
import com.ivan.final_project.models.ChangePassword;
import com.ivan.final_project.models.User;
import com.ivan.final_project.rest.ApiInterface;
import com.ivan.final_project.rest.RetrofitInstance;
import com.ivan.final_project.util.MySession;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UbahPasswordActivity extends AppCompatActivity {
    private static MySession session;
    private TextInputEditText tNewPassword, tNewRePassword;
    private String sNewPassword, sNewRePassword, sToken;
    private Button bEditPassword;
    ApiInterface apiInterface;
    private User newUserUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_password);

        session = new MySession(UbahPasswordActivity.this);

        tNewPassword = findViewById(R.id.txtNewPassword);
        tNewRePassword = findViewById(R.id.txtNewRePassword);
        bEditPassword = findViewById(R.id.btnEditPassword);

        if (session.isLoggedIn()) {
            //Get Session
            HashMap<String, String> sUsernya = session.getUserDetails();
            sToken = sUsernya.get(MySession.KEY_TOKEN);
            Log.d("PRINT", "token : "+sToken);
            apiInterface = RetrofitInstance.getRetrofitInstance(""+sToken).create(ApiInterface.class);
        }

        bEditPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sNewPassword = tNewPassword.getText().toString();
                sNewRePassword = tNewRePassword.getText().toString();
                if(!sNewPassword.equals(sNewRePassword)) {
                    Toast.makeText(UbahPasswordActivity.this, "Password tidak sama!", Toast.LENGTH_LONG).show();
                }
                else {
                    ChangePassword changePassword = new ChangePassword(sNewPassword);
                    Call<User> userCall = apiInterface.postChangePassword(changePassword);
                    userCall.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            Log.d("PRINT", "response : "+response.code());
                            newUserUpdate = response.body();
                            Log.d("PRINT", "newUserUpdate : "+newUserUpdate);
                            if(newUserUpdate != null) {
                                final AlertDialog.Builder builder = new AlertDialog.Builder(UbahPasswordActivity.this);
                                builder.setTitle("Berhasil");
                                builder.setMessage("Update Password Berhasil Dilakukan");
                                builder.setPositiveButton("Ok",(dialog, which) -> {
                                    finish();
                                });
                                builder.setCancelable(false);
                                builder.show();
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {

                        }
                    });
                }
            }
        });
    }
}