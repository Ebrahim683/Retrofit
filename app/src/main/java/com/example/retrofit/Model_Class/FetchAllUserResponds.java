package com.example.retrofit.Model_Class;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class FetchAllUserResponds {

    @SerializedName("users")
    List<User> userList;
    String error;

    public FetchAllUserResponds(List<User> userList, String error) {
        this.userList = userList;
        this.error = error;
    }

    public FetchAllUserResponds() {
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
