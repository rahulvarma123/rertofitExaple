package com.example.rertofitexaple;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserModel implements Serializable {

    @SerializedName("emailId")
    @Expose
    private String emailId;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("phNumber")
    @Expose
    private String phNumber;

    @SerializedName("userId")
    @Expose
    private Integer userId;

    @SerializedName("userName")
    @Expose
    private String userName;

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhNumber() {
        return phNumber;
    }

    public void setPhNumber(String phNumber) {
        this.phNumber = phNumber;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @NonNull
    @Override
    public String toString() {
        return userId + " - " + userName + " - " + emailId + " - " + phNumber;
    }
}