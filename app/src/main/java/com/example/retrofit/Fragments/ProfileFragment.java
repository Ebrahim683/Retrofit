package com.example.retrofit.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.retrofit.Model_Class.LoginResponds;
import com.example.retrofit.Model_Class.RetrofitClient;
import com.example.retrofit.Model_Class.UpdatePasswordResponds;
import com.example.retrofit.R;
import com.example.retrofit.SharedPreference.SharedPreferenceManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileFragment extends Fragment {

    EditText usernameE,emailE,currentP,newP;
    Button updateButton,updatePasswordButton;
    int id;
    String email;
    SharedPreferenceManager sharedPreferenceManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        usernameE = view.findViewById(R.id.nameSIDU);
        emailE = view.findViewById(R.id.emailSIDU);

        currentP = view.findViewById(R.id.currentPass);
        newP = view.findViewById(R.id.newPassword);

        updateButton = view.findViewById(R.id.updateAID);
        updatePasswordButton = view.findViewById(R.id.ButtonUpdatePassword);

        sharedPreferenceManager = new SharedPreferenceManager(getActivity());
        id = sharedPreferenceManager.getUser().getId();
        email = sharedPreferenceManager.getUser().getEmail();

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserAccount();
            }
        });


        updatePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserPassword();
            }
        });


        return view;
    }


    private void updateUserAccount() {
        String username = usernameE.getText().toString();
        String email = emailE.getText().toString().trim();

        if (username.isEmpty()){
            usernameE.setError("Enter User Name");
            usernameE.requestFocus();
            return;
        }
        if (email.isEmpty()){
            emailE.setError("Enter Email");
            emailE.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailE.setError("Enter Correct Email");
            emailE.requestFocus();
            return;
        }

        Call<LoginResponds> updateCall = RetrofitClient
                .getInstance()
                .getApi()
                .updateUserAccount(id,username,email);

        updateCall.enqueue(new Callback<LoginResponds>() {
            @Override
            public void onResponse(Call<LoginResponds> call, Response<LoginResponds> response) {
                LoginResponds updateResponds = response.body();
                if (response.isSuccessful()){
                    if (updateResponds.getError().equals("800")){
                        Toast.makeText(getActivity(), updateResponds.getMessage(), Toast.LENGTH_SHORT).show();
                        sharedPreferenceManager.saveUser(updateResponds.getUser());
                    }else {
                        Toast.makeText(getActivity(), updateResponds.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<LoginResponds> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void updateUserPassword() {

        String currentPass = currentP.getText().toString();
        String newPass = newP.getText().toString();

        if (currentPass.isEmpty()){
            currentP.setError("Enter Current Password");
            currentP.requestFocus();
            return;
        }
        if (newPass.isEmpty()){
            newP.setError("Enter New Password");
            newP.requestFocus();
            return;
        }
        if (currentPass.length()<6){
            currentP.setError("Password Length Should Be 6 Word");
            currentP.requestFocus();
            return;
        }
        if (newPass.length()<6){
            newP.setError("Password Length Should Be 6 Word");
            newP.requestFocus();
            return;
        }


        Call<UpdatePasswordResponds> updatePasswordRespondsCall = RetrofitClient
                .getInstance()
                .getApi()
                .updatePassword(email,currentPass,newPass);

        updatePasswordRespondsCall.enqueue(new Callback<UpdatePasswordResponds>() {
            @Override
            public void onResponse(Call<UpdatePasswordResponds> call, Response<UpdatePasswordResponds> response) {
                UpdatePasswordResponds updatePasswordResponds = response.body();
                if (response.isSuccessful()){
                    if (updatePasswordResponds.getError().equals("1000")){
                        Toast.makeText(getActivity(), updatePasswordResponds.getMessage(), Toast.LENGTH_SHORT).show();
                        currentP.setText("");
                        newP.setText("");
                    }
                }else {
                    Toast.makeText(getActivity(), "Fail", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UpdatePasswordResponds> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



    }


}