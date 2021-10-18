package com.evans.senditapp.data.responses

data class OrderResponse(
    val date: String,
    val description: String,
    val destination: String,
    val id: Int,
    val owner_id: Int,
    val pickup_location: String,
    val reciever_name: String,
    val reciever_number: String,
    val vehicle: String,
    val weight: String
)