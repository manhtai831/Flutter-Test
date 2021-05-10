package com.manhtai.fluttertest.itf;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.manhtai.fluttertest.model.BaseJson;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface GameApi {

    String baseUrl = "https://api.launcher.skw.vn/";

    Gson GSON = new GsonBuilder().setDateFormat("YYYY-MM-dd HH:mm:ss").create();

GameApi GAME_API =new Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create(GSON))
        .build()
        .create(GameApi.class);
    @FormUrlEncoded
    @POST("user/register")
    Call<BaseJson> rigisterUser(@Field("gameId") String gameId,
                                @Field("userName") String userName,
                                @Field("password") String password);
    @FormUrlEncoded
     @POST("user/login/skw")
    Call<BaseJson> loginUser(@Field("gameId") String gameId,
                                 @Field("userName") String userName,
                                 @Field("password") String password,
                                 @Field("appKey") String appKey,
                                 @Field("deviceInfo") String deviceInfo
                                 );
    @FormUrlEncoded
     @POST("user/login/guest")
    Call<BaseJson> loginUserGuest(@Field("gameId") String gameId,
                                 @Field("guestId") String guestId,
                                 @Field("appKey") String appKey,
                                 @Field("deviceInfo") String deviceInfo);

}
