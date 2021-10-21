package com.evans.senditapp.data.repository

import com.evans.senditapp.data.network.AuthApi
import retrofit2.http.Header

class AuthRepository(
    private val api: AuthApi
) : BaseRepository() {
    suspend fun login(
        email: String,
        password: String
    ) = safeApiCall { api.login(email, password) }

    suspend fun signUp(
        email: String,
        username: String,
        password: String
    ) = safeApiCall { api.signUp(email, username, password) }

    suspend fun postOrders(
        description: String,
        destination: String,
        pickup_location: String,
        reciever_name: String,
        reciever_number: String,
        vehicle: String,
        weight: String
    ) = safeApiCall { api.postOrders(description, destination, pickup_location, reciever_name, reciever_number, vehicle, weight) }

    suspend fun getData (@Header("Bearer token here")access: String) = safeApiCall { api.getData("") }
}
