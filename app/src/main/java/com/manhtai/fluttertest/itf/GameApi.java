package com.manhtai.fluttertest.itf;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.manhtai.fluttertest.model.BaseJson;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;

public interface GameApi {

    String baseUrl = "https://api.launcher.skw.vn/";

    Gson GSON = new GsonBuilder().setDateFormat("YYYY-MM-dd HH:mm:ss").create();

GameApi GAME_API =new Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(GSON))
        .build()
        .create(GameApi.class);

    @Headers("Content-Type: application/json")
    @POST("user/register")
    Call<BaseJson> registerUser(@Body String body);

    @Headers("Content-Type: application/json")
     @POST("user/login/skw")
    Call<BaseJson> loginUser(@Body String body);

    @Headers("Content-Type: application/json")
     @POST("user/login/guest")
    Call<BaseJson> loginUserGuest(@Body String body);

}
