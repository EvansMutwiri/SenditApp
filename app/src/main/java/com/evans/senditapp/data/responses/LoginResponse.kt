package com.evans.senditapp.data.responses

data class LoginResponse(
    val email: String,
    val tokens: String,
    val username: String
)