package com.evans.senditapp.data.models

data class OrderResponse(
    val description: String,
    val destination: String,
    val id: Int,
    val owner_id: Int,
    val pickup_location: String,
    val reciever_name: String,
    val reciever_number: String,
    val vehicle: String,
    val weight: String,
    val date : String
)