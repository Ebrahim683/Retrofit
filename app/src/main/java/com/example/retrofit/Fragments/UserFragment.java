package com.example.retrofit.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.retrofit.Adapters.UserAdapter;
import com.example.retrofit.Model_Class.FetchAllUserResponds;
import com.example.retrofit.Model_Class.RetrofitClient;
import com.example.retrofit.Model_Class.User;
import com.example.retrofit.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserFragment extends Fragment {

    RecyclerView recyclerView;
    List<User> userList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.fragment_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerID);

        try {
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }catch (Exception e){
            Log.e("error1","Error = "+e);
        }

        Call<FetchAllUserResponds> fetchAllUserRespondsCall = RetrofitClient
                .getInstance()
                .getApi()
                .fetchAllUser();

        fetchAllUserRespondsCall.enqueue(new Callback<FetchAllUserResponds>() {
            @Override
            public void onResponse(Call<FetchAllUserResponds> call, Response<FetchAllUserResponds> response) {

                if (response.isSuccessful()){
                    try {
                       userList = response.body().getUserList();
                       recyclerView.setAdapter(new UserAdapter(getActivity(),userList));
                    }catch (Exception e){
                        Log.e("error2","Error = "+e);
                    }
                }else {
                    Toast.makeText(getActivity(), response.body().getError(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FetchAllUserResponds> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}