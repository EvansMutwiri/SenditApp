package com.evans.senditapp.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.evans.senditapp.data.network.Resource
import com.evans.senditapp.data.repository.AuthRepository
import com.evans.senditapp.data.models.LoginResponse
import com.evans.senditapp.data.models.OrderResponse
import com.evans.senditapp.data.models.SignupResponse
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repository: AuthRepository
): ViewModel() {

    private val _loginResponse : MutableLiveData<Resource<LoginResponse>> = MutableLiveData()
    val loginResponse: LiveData<Resource<LoginResponse>>
    get() = _loginResponse

    private val _signupResponse : MutableLiveData<Resource<SignupResponse>> = MutableLiveData()
    val signupResponse: LiveData<Resource<SignupResponse>>
        get() = _signupResponse

    private val _orderResponse : MutableLiveData<Resource<OrderResponse>> = MutableLiveData()
    val orderResponse : LiveData<Resource<OrderResponse>>
    get() = _orderResponse

    fun login (
        email: String,
        password: String
    ) = viewModelScope.launch {
        _loginResponse.value = repository.login(email, password)
    }

    fun signUp (
        email: String,
        username: String,
        password: String
    ) = viewModelScope.launch {
        _signupResponse.value = repository.signUp(email, username, password)
    }

    fun postOrders (
        description: String,
        destination: String,
        pickup_location: String,
        reciever_name: String,
        reciever_number: String,
        vehicle: String,
        weight: String
    ) = viewModelScope.launch {
        _orderResponse.value = repository.postOrders(description, destination, pickup_location, reciever_name, reciever_number, vehicle, weight)
    }

//    fun getData(access: String) = viewModelScope.launch {
//        _orderResponse.value = repository.getData("Bearer token")
//    }
}