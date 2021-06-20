package com.ivan.final_project.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.ArrayMap;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.ivan.final_project.R;
import com.ivan.final_project.models.ResponseUser;
import com.ivan.final_project.models.User;
import com.ivan.final_project.rest.ApiClient;
import com.ivan.final_project.rest.ApiInterface;
import com.ivan.final_project.util.MySession;

import org.json.JSONObject;

import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private String PasswordHolder, EmailHolder;
    private TextInputEditText edUser, edPassword;
    private TextView textRegister;
    private Button bLogin;
    private Boolean CheckEditText;

    private MySession ms;

    private ApiInterface apiInterface;
    private User listUser;
    private String xToken = "";
    private MySession session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //Session
        session = new MySession(LoginActivity.this);
        //Api Service use Retrofit
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        ms = new MySession(getApplicationContext());
        if(ms.isLoggedIn()) {
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
            finish();
        }

        edUser = findViewById(R.id.username);
        edPassword = findViewById(R.id.password);
        bLogin = findViewById(R.id.loginBtn);
        textRegister = findViewById(R.id.txtRegister);

        bLogin.setOnClickListener(view -> cekLoginTwo());

        textRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(i);
            }
        });
    }

    private void CheckEditTextIsEmptyOrNot() {
        EmailHolder = edUser.getText().toString();
        PasswordHolder = edPassword.getText().toString();
        if(TextUtils.isEmpty(EmailHolder) || TextUtils.isEmpty(PasswordHolder)) {
            CheckEditText = false;
        }
        else {
            CheckEditText = true;
        }
    }

    private void cekLoginTwo() {
        CheckEditTextIsEmptyOrNot();
        if(CheckEditText) {
            SyncCek();
        }
        else {
            Toast.makeText(LoginActivity.this, "Data masih kosong!", Toast.LENGTH_LONG).show();
        }
    }

    private void SyncCek() {
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("please wait");
        progressDialog.show();

        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(2000);
                    if(progressDialog.isShowing()) progressDialog.dismiss();
                    callValidateTrue();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void callValidateTrue() {
        try {
            runOnUiThread(() -> {
                Map<String, Object> jsonParams = new ArrayMap<>();
                jsonParams.put("email", EmailHolder);
                jsonParams.put("password", PasswordHolder);
                Log.d("PRINT", ""+jsonParams);

                RequestBody body = RequestBody.create(
                        MediaType.parse("application/json; charset=utf-8"),(
                                new JSONObject(jsonParams)).toString());
                Log.d("PRINT", ""+body.toString());
                Call<ResponseUser> callUserList = apiInterface.getLogin(body);
                callUserList.enqueue(new Callback<ResponseUser>() {
                    @Override
                    public void onResponse(Call<ResponseUser> call, Response<ResponseUser> response) {
                        try {
                            if (response.body() != null){
                                xToken = response.body().getAccessToken();
                                listUser = response.body().getUser();

                                int xId  = listUser.getId();
                                String xEmail = listUser.getEmail();
                                String xfirstName  = listUser.getFirstName();
                                String xlastName  = listUser.getLastName();
                                String xmobileNumber  = listUser.getMobileNumber();
                                Log.d("PRINT", ""+xEmail+" "+xfirstName+" "+xlastName+" "+xmobileNumber+" "+xToken);
                                session.createLoginSession(xId, xEmail, xfirstName, xlastName, xmobileNumber, 0, "titleRoles", xToken);

                                //Start Activity
                                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(i);

                                //Toast Activity
                                Toast.makeText(LoginActivity.this, "Berhasil Login !!\nAnda Login Sebagai user", Toast.LENGTH_SHORT).show();

                                //Destroy Login
                                finish();

//                                generateUserList();
                            }else {
                                Log.d("Login : ", response.message().toString());
                                Toast.makeText(getApplicationContext(), "Username/Passsword Tidak Sesuai !!", Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                            Log.wtf("Error : ",e.getMessage());
                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseUser> call, Throwable t) {
                        t.printStackTrace();
                        Log.wtf("Failure : ",t.getMessage());
                    }
                });
            });
        }catch (Exception e){
            e.printStackTrace();
            Log.wtf("Error Exception : ",e.getMessage());
        }
    }

    //Get Data Retrofit
    private void generateUserList() {
        int lengthRoles = listUser.getRoles().size();
        final String[] sTitleRoles = new String[lengthRoles];
        final Integer[] sIdRoles = new Integer[lengthRoles];

        for (int i=0; i < lengthRoles; i++){
            sTitleRoles[i] = listUser.getRoles().get(i).getRole();
            sIdRoles[i] = listUser.getRoles().get(i).getId();
        }

        menuDialog(sIdRoles, sTitleRoles);
    }

    //View Dialog Roles
    private void menuDialog(Integer[] idRoles,  String[] titleRoles){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Menu Roles");
        alertDialog.setCancelable(false);
        alertDialog.setItems(titleRoles, (dialog, which) -> {
            //String selectedPre = Arrays.asList(sPre).get(which);
            String xTitle = titleRoles[which];
            int xIdRoles = idRoles[which];
            callRoles(xIdRoles, xTitle);
            dialog.dismiss();
        });
        AlertDialog dialog = alertDialog.create();
        dialog.show();
    }

    //Call Roles
    private void callRoles(int idRoles , String titleRoles){
        //Generate Session
        int xId  = listUser.getId();
        String xEmail = listUser.getEmail();
        String xfirstName  = listUser.getFirstName();
        String xlastName  = listUser.getLastName();
        String xmobileNumber  = listUser.getMobileNumber();
        Log.d("PRINT", ""+xEmail+" "+xfirstName+" "+xlastName+" "+xmobileNumber+" "+xToken);
        session.createLoginSession(xId, xEmail, xfirstName, xlastName, xmobileNumber, idRoles, titleRoles, xToken);

        //Start Activity
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);

        //Toast Activity
        Toast.makeText(this, "Berhasil Login !!\nAnda Login Sebagai "+titleRoles+"...", Toast.LENGTH_SHORT).show();

        //Destroy Login
        finish();
    }
}