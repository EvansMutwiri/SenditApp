package com.evans.senditapp.data.network

import com.evans.senditapp.data.responses.LoginResponse
import com.evans.senditapp.data.responses.OrderResponse
import com.evans.senditapp.data.responses.SignupResponse
import retrofit2.http.*
import java.util.*

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

    @FormUrlEncoded
    @POST("order/v1/order/")
    suspend fun postOrders(
        @Field("date") date: String,
        @Field("vehicle") vehicle: String,
        @Field("description") description: String,
        @Field("pickup_location") pickup_location: String,
        @Field("destination") destination: String,
        @Field("reciever_name") reciever_name: String,
        @Field("reciever_number") reciever_number: String,
        @Field("weight") weight: String,
    ) : OrderResponse

    @GET("order/v1/order/")
    suspend fun getOrders()

    @DELETE("order/{id}/")
    suspend fun deleteById(
        @Path("id") id: Int
    )
}