package com.evans.senditapp.data.repository

import com.evans.senditapp.data.network.AuthApi

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
}
