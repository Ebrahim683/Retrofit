package com.example.retrofit.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.retrofit.Fragments.DashboardFragment;
import com.example.retrofit.Fragments.ProfileFragment;
import com.example.retrofit.Model_Class.DeleteUserResponds;
import com.example.retrofit.Model_Class.RetrofitClient;
import com.example.retrofit.R;
import com.example.retrofit.Fragments.UserFragment;
import com.example.retrofit.SharedPreference.SharedPreferenceManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;
    RelativeLayout relativeLayout;
    SharedPreferenceManager sharedPreferenceManager;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationView = findViewById(R.id.bottomNavID);
//        relativeLayout = findViewById(R.id.relativeLayoutID);

        sharedPreferenceManager = new SharedPreferenceManager(getApplicationContext());

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        LoadFragment(new DashboardFragment());

        id = sharedPreferenceManager.getUser().getId();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment fragment = null;

        switch (item.getItemId()){

            case R.id.dashboardID:
                fragment = new DashboardFragment();
                break;

            case R.id.userID:
                fragment = new UserFragment();
                break;

            case R.id.profileID:
                fragment = new ProfileFragment();
                break;
        }

        if (fragment!=null){
            LoadFragment(fragment);
        }

        return true;
    }

    public void LoadFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.relativeLayoutID,fragment).commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menus,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.logOutID:
                userLogOut();
                break;

            case R.id.deleteID:
                userDelete();
                break;

        }

        return super.onOptionsItemSelected(item);
    }


    private void userLogOut() {
        sharedPreferenceManager.logOut();
        Intent intent = new Intent(HomeActivity.this,LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        Toast.makeText(this, "Logged Out", Toast.LENGTH_SHORT).show();
    }

    private void userDelete() {

        Call<DeleteUserResponds> deleteUserRespondsCall = RetrofitClient
                .getInstance()
                .getApi()
                .deleteUser(id);

        deleteUserRespondsCall.enqueue(new Callback<DeleteUserResponds>() {
            @Override
            public void onResponse(Call<DeleteUserResponds> call, Response<DeleteUserResponds> response) {

                DeleteUserResponds deleteUserResponds = response.body();

                if (response.isSuccessful()){
                    if (deleteUserResponds.getError().equals("1300")){
                        userLogOut();
                        Toast.makeText(HomeActivity.this, deleteUserResponds.getMessage(), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(HomeActivity.this, deleteUserResponds.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(HomeActivity.this,"Failed", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<DeleteUserResponds> call, Throwable t) {
                Toast.makeText(HomeActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}