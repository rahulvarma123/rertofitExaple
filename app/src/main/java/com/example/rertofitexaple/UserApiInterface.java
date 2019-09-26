package com.example.rertofitexaple;

import android.service.autofill.UserData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserApiInterface {

    @GET("/getAllUsers")
    public Call<UserDataList> getAllUserData();

    @POST("/signup")
    public Call<UserModel> PostNewUser(@Body UserModel userModel);

    @PUT("/updateUser")
    public Call<UserModel> updateExistingUser(@Body UserModel userModel);

    @DELETE("/deleteUser/{userId}")
    public Call<UserModel> deleteUser(@Path("userId") int userId);
}
