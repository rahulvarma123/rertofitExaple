package com.example.rertofitexaple;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserDataList {

    public List<UserModel> getUsers() {
        return userList;
    }

    public void setUsers(List<UserModel> users) {
        userList = users;
    }

    @SerializedName("Users")
    @Expose
    private List<UserModel> userList;
}
