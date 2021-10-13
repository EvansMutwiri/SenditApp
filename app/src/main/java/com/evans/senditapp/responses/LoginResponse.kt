package com.evans.senditapp.responses

data class LoginResponse(
    val email: String,
    val tokens: String,
    val username: String
)