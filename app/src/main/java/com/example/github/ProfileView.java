package com.example.github;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.github.api.APIService;
import com.example.github.api.RetrofitClient;
import com.example.github.databinding.ActivityProfileViewBinding;
import com.example.github.model.UserData;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileView extends AppCompatActivity {

    APIService apiService;
    ActivityProfileViewBinding binding;
    String userProfile;
    UserData userDataProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_view);
        Objects.requireNonNull(getSupportActionBar()).hide();

        initialize();
    }

    private void initialize() {
        binding = ActivityProfileViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle bundle = getIntent().getExtras();
        userProfile = (String) bundle.getString("userProfile");

        apiService = RetrofitClient.getClient(MainActivity.BASE_URL).create(APIService.class);

        getDataFromAPI();
    }

    private void getDataFromAPI() {
        apiService.getUserDetails(userProfile).enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {
                userDataProfile = response.body();
                fillProfile();
            }

            @Override
            public void onFailure(Call<UserData> call, Throwable t) {
            }
        });
    }

    private void fillProfile() {
        Glide.with(this)
                .load(userDataProfile.getAvatarUrl())
                .into(binding.imageUserProfile);
        binding.textUserNameProfile.setText("User Name: " + userDataProfile.getLogin());
        binding.textNameProfile.setText("Name: " + userDataProfile.getName());
        binding.textDescriptionProfile.setText("Location: " + userDataProfile.getLocation());
        binding.textFollowersProfile.setText(userDataProfile.getFollowers().toString() + " followers");
        binding.textFollowingProfile.setText(userDataProfile.getFollowing().toString() + " following");
    }
}