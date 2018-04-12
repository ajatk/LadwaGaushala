package com.example.ragasoft.ladwagaushala.retrofit;

import com.example.ragasoft.ladwagaushala.model.RegisteredModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by sam on 4/10/2018.
 */

public interface ApiInterface {

    @FormUrlEncoded
    @POST("user/register")
    Call<RegisteredModel> getRegisterCall(@Field("name") String name_, @Field("email") String email_, @Field("password") String password_, @Field("mobileNo") String mobileNo_);

    @FormUrlEncoded
    @POST("user/login")
    Call<RegisteredModel> getLoginCall(@Field("email")String email_, @Field("password") String password_);
}
