package com.example.retrofit.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.retrofit.R;
import com.example.retrofit.SharedPreference.SharedPreferenceManager;

public class DashboardFragment extends Fragment {

    TextView nameT,emailT;
    SharedPreferenceManager sharedPreferenceManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        nameT = view.findViewById(R.id.nameT);
        emailT = view.findViewById(R.id.emailT);

        sharedPreferenceManager = new SharedPreferenceManager(getActivity());

        String userName = "Hay! "+sharedPreferenceManager.getUser().getUsername();
        String userEmail = "Email:"+sharedPreferenceManager.getUser().getEmail();
        nameT.setText(userName);
        emailT.setText(userEmail);


        return view;
    }
}