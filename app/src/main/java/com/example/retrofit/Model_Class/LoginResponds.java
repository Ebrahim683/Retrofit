package com.example.retrofit.Model_Class;

public class LoginResponds {

    User user;
    String error;
    String message;

    public LoginResponds(User user, String error, String message) {
        this.user = user;
        this.error = error;
        this.message = message;
    }

    public LoginResponds() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
