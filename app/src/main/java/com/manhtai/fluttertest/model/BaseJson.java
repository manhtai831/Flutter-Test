package com.manhtai.fluttertest.model;

import com.google.gson.annotations.SerializedName;

public class BaseJson {
    @SerializedName("requestId")
    private String requestId;
    @SerializedName("at")
    private String at;
    @SerializedName("error")
    private Error error;
    @SerializedName("data")
    private Data data;


    public BaseJson(String requestId, String at, Error error, Data data) {
        this.requestId = requestId;
        this.at = at;
        this.error = error;
        this.data = data;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getAt() {
        return at;
    }

    public void setAt(String at) {
        this.at = at;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
