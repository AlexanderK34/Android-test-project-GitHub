package com.example.github.api;

import com.example.github.model.FollowersOrFollowingData;
import com.example.github.model.UserData;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface APIService {

    @GET("/users/{user}")
    Call<UserData> getUserDetails(
            @Path("user") String user
    );

    @GET("/users/{user}/followers")
    Call<ArrayList<FollowersOrFollowingData>> getListFollowers(
            @Path("user") String user
    );

    @GET("/users/{user}/following")
    Call<ArrayList<FollowersOrFollowingData>> getListFollowing(
            @Path("user") String user
    );
}
