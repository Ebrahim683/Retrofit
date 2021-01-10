package com.example.retrofit.Model_Class;

public class DeleteUserResponds {

    private String error,message;

    public DeleteUserResponds(String error, String message) {
        this.error = error;
        this.message = message;
    }

    public DeleteUserResponds() {
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
