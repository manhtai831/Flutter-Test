package com.manhtai.fluttertest.model;

import com.google.gson.annotations.SerializedName;

import retrofit2.http.Field;

public class Error {
    @SerializedName("code")
    private int code;
    @SerializedName("message")
    private String message;

    public Error(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
