package com.example.retrofit.Activitys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.retrofit.Model_Class.LoginResponds;
import com.example.retrofit.Model_Class.RetrofitClient;
import com.example.retrofit.R;
import com.example.retrofit.SharedPreference.SharedPreferenceManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    TextView tCreate;
    Button loginBTN;
    EditText etEmail,etPassword;
    String email,password;
    SharedPreferenceManager sharedPreferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tCreate = findViewById(R.id.createText);
        loginBTN = findViewById(R.id.loginID);
        etEmail = findViewById(R.id.LemailSID);
        etPassword = findViewById(R.id.LpasswordSID);
        sharedPreferenceManager = new SharedPreferenceManager(getApplicationContext());

        tCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        loginBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });


    }

    public void loginUser(){

        email = etEmail.getText().toString().trim();
        password = etPassword.getText().toString();

        if (email.isEmpty()){
            etEmail.setError("Enter Email");
            etEmail.requestFocus();
            return;
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            etEmail.setError("Enter Correct Email");
            etEmail.requestFocus();
            return;
        }
        else if (password.isEmpty()){
            etPassword.setError("Enter Password");
            etPassword.requestFocus();
            return;
        }
        else if (password.length()<6){
            etPassword.setError("Minimum Length of Password is 6");
            etPassword.requestFocus();
            return;
        }

        Call<LoginResponds> loginRespondsCall = RetrofitClient
                .getInstance()
                .getApi()
                .login(email,password);

        loginRespondsCall.enqueue(new Callback<LoginResponds>() {
            @Override
            public void onResponse(Call<LoginResponds> call, Response<LoginResponds> response) {
                LoginResponds loginResponds = response.body();
                if (response.isSuccessful()){
                    if (loginResponds.getError().equals("400")){

                        sharedPreferenceManager.saveUser(loginResponds.getUser());
                        Toast.makeText(LoginActivity.this, loginResponds.getMessage(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();

                    }else {
                        Toast.makeText(LoginActivity.this,loginResponds.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponds> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }


    @Override
    protected void onStart() {
        super.onStart();

        if (sharedPreferenceManager.isLoggedIn()){
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }

    }
}