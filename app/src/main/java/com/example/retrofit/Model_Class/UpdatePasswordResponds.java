package com.example.retrofit.Model_Class;

public class UpdatePasswordResponds {

    String error,message;

    public UpdatePasswordResponds(String error, String message) {
        this.error = error;
        this.message = message;
    }


    public UpdatePasswordResponds() {
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
