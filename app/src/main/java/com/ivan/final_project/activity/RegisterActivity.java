package com.ivan.final_project.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.ivan.final_project.R;
import com.ivan.final_project.models.User;
import com.ivan.final_project.models.UserRegister;
import com.ivan.final_project.rest.ApiClient;
import com.ivan.final_project.rest.ApiInterface;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private TextView textLogin;
    private TextView btnLogin;
    private Intent intent;

    private Button btnRegister;
    private ApiInterface apiInterface;
    private TextInputEditText edEmail, edFirstName, edLastName, edMobileNumber, edPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        textLogin = findViewById(R.id.txtPunyaAkun);
        btnRegister = findViewById(R.id.btnRegister);
        edEmail = findViewById(R.id.txtEmail);
        edFirstName = findViewById(R.id.txtFirstname);
        edLastName = findViewById(R.id.txtLastname);
        edMobileNumber = findViewById(R.id.txtMobileNumber);
        edPassword = findViewById(R.id.txtPassword);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        btnRegister.setOnClickListener(v -> {
            if(edFirstName.getText().toString().length()==0 || edLastName.getText().toString().length()==0 || edEmail.getText().toString().length()==0 || edPassword.getText().toString().length()==0 || edMobileNumber.getText().toString().length()==0) {
                if(edFirstName.getText().toString().length()==0 ) {
                    edFirstName.setError("Firstname diperlukan!");
                }
                if(edLastName.getText().toString().length()==0) {
                    edLastName.setError("Lastname diperlukan!");
                }
                if(edEmail.getText().toString().length()==0) {
                    edEmail.setError("Email diperlukan!");
                }
                if(edPassword.getText().toString().length()==0) {
                    edPassword.setError("Password diperlukan!");
                }
                if(edMobileNumber.getText().toString().length()==0) {
                    edMobileNumber.setError("Mobile number diperlukan!");
                }
            }
            else {
                ArrayList<String> role = new ArrayList<String>();
                role.add("user");
                UserRegister userRegister = new UserRegister(
                        edEmail.getText().toString(),
                        edFirstName.getText().toString(),
                        edLastName.getText().toString(),
                        edMobileNumber.getText().toString(),
                        edPassword.getText().toString(),role);
                Call<User> userCall = apiInterface.createUser(userRegister);
                userCall.enqueue(new Callback<User>() {

                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        User user = response.body();
                        if (user != null){
                            final AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                            builder.setTitle("information");
                            builder.setMessage("Registrasi Berhasil Dilakukan");
                            builder.setPositiveButton("Ok",(dialog, which) -> {
                                Intent home=new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(home);
                                finish();
                            });

                            builder.setCancelable(false);
                            builder.show();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(RegisterActivity.this, "Register gagal! Mohon coba lagi", Toast.LENGTH_LONG).show();
                    }
                });
            }

        });

        textLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
            }
        });
    }
}