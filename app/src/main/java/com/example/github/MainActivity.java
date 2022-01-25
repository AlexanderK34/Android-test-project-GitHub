package com.example.github;

/*
00-00 learn API
00-40 create app, main layout
01-15 create main activity (search, binding, onclicklistener)
01-45 add model, create API Service
02-30 parsing JSON in MainActivity
03-05 find errors, view
03-50 output imageview
04-10 sent intent, create 2-nd view
05-00 create model followers
05-30 create cell for recyclerView, create recyclerViewAdapter
07-00 find problem parsing followers array (api error)
09-00 find problem with recyclerView (refresh recycler)
10-00 add followers and following to recyclerView
10-40 create profile view and activity
10-50 create activity
11-10 refactoring, names
11-50
*/

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;

import com.bumptech.glide.Glide;
import com.example.github.api.APIService;
import com.example.github.api.RetrofitClient;
import com.example.github.databinding.ActivityMainBinding;
import com.example.github.model.UserData;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements SearchView.OnQueryTextListener,
        View.OnClickListener {

    public static final String BASE_URL = "https://api.github.com";
    String searchQuery;
    UserData userData;
    APIService apiService;
    ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();

        initialize();
        initializeSearchView();
    }

    private void initialize() {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        apiService = RetrofitClient.getClient(BASE_URL).create(APIService.class);

        binding.textFollowers.setOnClickListener(this);
        binding.textFollowing.setOnClickListener(this);
    }

    private void initializeSearchView() {
        binding.searchUserView.setOnQueryTextListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.textFollowers:
                if (userData != null) {
                    goToViewFollowersOrFollowing(userData.getLogin(), true);
                }
                break;
            case R.id.textFollowing:
                if (userData != null) {
                    goToViewFollowersOrFollowing(userData.getLogin(), false);
                }
                break;
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        searchQuery = query;
        getUserDetails(query);
        binding.searchUserView.setQuery(null, false); // Clear text in SearchUserView
        binding.searchUserView.clearFocus();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    private void getUserDetails(String user) {
        apiService.getUserDetails(user).enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {
                userData = response.body();
                setUserInfoToLayout();
            }

            @Override
            public void onFailure(Call<UserData> call, Throwable t) {
            }
        });
    }

    private void setUserInfoToLayout() {
        if (userData == null) {
            binding.imageUser.setImageResource(0);
            binding.textUserName.setText(" === Not found === ");
            binding.textName.setText("");
            binding.textDescription.setText("");
            binding.textFollowers.setText("");
            binding.textFollowing.setText("");
        } else {
            Glide.with(this)
                    .load(userData.getAvatarUrl())
                    .into(binding.imageUser);
            binding.textUserName.setText("User Name: " + userData.getLogin());
            binding.textName.setText("Name: " + userData.getName());
            binding.textDescription.setText("Location: " + userData.getLocation());
            binding.textFollowers.setText(userData.getFollowers().toString() + " followers");
            binding.textFollowing.setText(userData.getFollowing().toString() + " following");
        }
    }

    private void goToViewFollowersOrFollowing(String user, boolean followers) {
        Intent intent = new Intent(MainActivity.this, ListFollowersOrFollowing.class);
        Bundle bundle = new Bundle();
        bundle.putString("user", user);
        bundle.putBoolean("followers",followers);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}