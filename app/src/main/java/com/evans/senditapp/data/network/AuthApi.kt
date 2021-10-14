package com.evans.senditapp.data.network

import com.evans.senditapp.data.responses.LoginResponse
import com.evans.senditapp.data.responses.SignupResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApi {

    @FormUrlEncoded
    @POST("auth/login/")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ) : LoginResponse

    @FormUrlEncoded
    @POST("auth/v1/register/")
    suspend fun signUp(
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("password") password: String,
    ) : SignupResponse
}