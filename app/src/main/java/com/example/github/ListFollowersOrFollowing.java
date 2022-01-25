package com.example.github;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;

import com.example.github.api.APIService;
import com.example.github.api.RetrofitClient;
import com.example.github.databinding.ActivityListOfFollowersOrFollowingBinding;
import com.example.github.model.FollowersOrFollowingData;
import com.example.github.model.FollowersOrFollowingDataCollection;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListFollowersOrFollowing extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    ActivityListOfFollowersOrFollowingBinding binding;
    APIService apiService;
    String user;
    Boolean followers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_followers_or_following);
        Objects.requireNonNull(getSupportActionBar()).hide();

        initialize();
        initializeRecyclerView();
        initializeAPIService();
    }

    private void initialize() {

        FollowersOrFollowingDataCollection.followersOrFollowingDataArrayList = new ArrayList<>();

        Bundle bundle = getIntent().getExtras();
        user = bundle.getString("user");
        followers = bundle.getBoolean("followers");
    }

    private void initializeRecyclerView() {
        binding = ActivityListOfFollowersOrFollowingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recyclerView = binding.recyclerViewListFollowersOrFollowing;
        recyclerViewAdapter = new RecyclerViewAdapter(this);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(1, RecyclerView.VERTICAL));
    }

    private void initializeAPIService() {

        apiService = RetrofitClient.getClient(MainActivity.BASE_URL).create(APIService.class);

        if (followers) {
            getFollowersFromAPI();
        } else {
            getFollowingFromAPI();
        }
    }

    private void getFollowersFromAPI() {

        apiService.getListFollowers(user).enqueue(new Callback<ArrayList<FollowersOrFollowingData>>() {
            @Override
            public void onResponse(Call<ArrayList<FollowersOrFollowingData>> call,
                                   Response<ArrayList<FollowersOrFollowingData>> response) {
                FollowersOrFollowingDataCollection.followersOrFollowingDataArrayList = response.body();
                recyclerViewAdapter.notifyDataSetChanged();   // Refresh adapter
            }

            @Override
            public void onFailure(Call<ArrayList<FollowersOrFollowingData>> call, Throwable t) {
            }
        });
    }

    private void getFollowingFromAPI() {

        apiService.getListFollowing(user).enqueue(new Callback<ArrayList<FollowersOrFollowingData>>() {
            @Override
            public void onResponse(Call<ArrayList<FollowersOrFollowingData>> call,
                                   Response<ArrayList<FollowersOrFollowingData>> response) {
                FollowersOrFollowingDataCollection.followersOrFollowingDataArrayList = response.body();
                recyclerViewAdapter.notifyDataSetChanged();   // Refresh adapter
            }

            @Override
            public void onFailure(Call<ArrayList<FollowersOrFollowingData>> call, Throwable t) {
            }
        });
    }
}