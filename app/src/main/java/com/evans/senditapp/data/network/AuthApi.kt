package com.evans.senditapp.data.network

import com.evans.senditapp.data.models.LoginResponse
import com.evans.senditapp.data.models.OrderResponse
import com.evans.senditapp.data.models.SignupResponse
import retrofit2.Call
import retrofit2.http.*
import java.util.*
import kotlin.collections.ArrayList

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

    class Order(
        val vehicle: String,
        val description: String,
        val pickup_location: String,
        val destination: String,
        val reciever_name: String,
        val reciever_number: String,
        val weight: String,
        val date: String
    )


//    @FormUrlEncoded
    @POST("order/v1/order/")
    suspend fun postOrders( @Header("Authorization") token: String,
                            @Body order: Order
    ) : OrderResponse

    @GET("order/v1/order/")
    fun getData(@Header("Authorization") token: String): Call<ArrayList<OrderResponse>>

    @DELETE("order/{id}/")
    suspend fun deleteById(
        @Path("id") id: Int
    )
}