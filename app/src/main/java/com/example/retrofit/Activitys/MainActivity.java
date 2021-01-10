package com.example.retrofit.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.retrofit.Model_Class.RegisterResponds;
import com.example.retrofit.Model_Class.RetrofitClient;
import com.example.retrofit.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    TextView tLogin;
    Button signUpBTN;
    EditText etName,etEmail,etPassword;
    String name,email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tLogin = findViewById(R.id.loginText);
        signUpBTN = findViewById(R.id.signUpID);
        etName = findViewById(R.id.nameSID);
        etEmail = findViewById(R.id.emailSID);
        etPassword = findViewById(R.id.passwordSID);

        tLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        signUpBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

    }

    private void registerUser() {

        name = etName.getText().toString();
        email = etEmail.getText().toString().trim();
        password = etPassword.getText().toString();

        if (name.isEmpty()){
            etName.setError("Enter Name");
            etName.requestFocus();
            return;
        }
        else if (email.isEmpty()){
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

        Call<RegisterResponds> registerRespondsCall = RetrofitClient
                .getInstance()
                .getApi()
                .register(name,email,password);

        registerRespondsCall.enqueue(new Callback<RegisterResponds>() {
            @Override
            public void onResponse(Call<RegisterResponds> call, Response<RegisterResponds> response) {

                RegisterResponds registerResponds = response.body();
                if (response.isSuccessful()){
                    Toast.makeText(MainActivity.this, registerResponds.getMessage(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(MainActivity.this, registerResponds.getMessage(), Toast.LENGTH_SHORT).show();
                }


            }
            @Override
            public void onFailure(Call<RegisterResponds> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        etName.setText("");
        etEmail.setText("");
        etPassword.setText("");

    }
}