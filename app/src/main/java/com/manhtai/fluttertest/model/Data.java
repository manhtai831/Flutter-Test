package com.manhtai.fluttertest.model;

import com.google.gson.annotations.SerializedName;

public class Data {
    @SerializedName("gameId")
    private String gameId;
    @SerializedName("userName")
    private String userName;
    @SerializedName("session")
    private String session;
    @SerializedName("id")
    private String id;
    @SerializedName("cgUuid")
    private String cgUuid;
    @SerializedName("accessToken")
    private String accessToken;
    @SerializedName("deviceId")
    private String deviceId;

    public Data(String gameId, String userName, String session, String id, String cgUuid, String accessToken, String deviceId) {
        this.gameId = gameId;
        this.userName = userName;
        this.session = session;
        this.id = id;
        this.cgUuid = cgUuid;
        this.accessToken = accessToken;
        this.deviceId = deviceId;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCgUuid() {
        return cgUuid;
    }

    public void setCgUuid(String cgUuid) {
        this.cgUuid = cgUuid;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
