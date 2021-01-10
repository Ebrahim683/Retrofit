package com.example.retrofit;

import com.example.retrofit.Model_Class.DeleteUserResponds;
import com.example.retrofit.Model_Class.FetchAllUserResponds;
import com.example.retrofit.Model_Class.LoginResponds;
import com.example.retrofit.Model_Class.RegisterResponds;
import com.example.retrofit.Model_Class.UpdatePasswordResponds;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Api {

    @FormUrlEncoded
    @POST("register.php")
    Call<RegisterResponds> register(
            @Field("username") String username,
            @Field("email") String email,
            @Field("password") String password
    );


    @FormUrlEncoded
    @POST("login.php")
    Call<LoginResponds> login(
            @Field("email") String email,
            @Field("password") String password
    );


    @GET("fetchAllUser.php")
    Call<FetchAllUserResponds> fetchAllUser(

    );


    @FormUrlEncoded
    @POST("updateUser.php")
    Call<LoginResponds> updateUserAccount(
            @Field("id") int id,
            @Field("username") String username,
            @Field("email") String email
    );


    @FormUrlEncoded
    @POST("updatePassword.php")
    Call<UpdatePasswordResponds> updatePassword(
            @Field("email") String email,
            @Field("currentPassword") String currentPassword,
            @Field("newPassword") String newPassword
    );


    @FormUrlEncoded
    @POST("deleteUser.php")
    Call<DeleteUserResponds> deleteUser(
            @Field("id") int id
    );




}
