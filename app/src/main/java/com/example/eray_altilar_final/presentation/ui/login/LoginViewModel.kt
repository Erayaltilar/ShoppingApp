package com.example.eray_altilar_final.presentation.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eray_altilar_final.core.Resource
import com.example.eray_altilar_final.domain.model.LoginRequest
import com.example.eray_altilar_final.domain.model.LoginResponse
import com.example.eray_altilar_final.domain.model.usermodel.Users
import com.example.eray_altilar_final.domain.usecase.GetUsersUseCase
import com.example.eray_altilar_final.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    val getUsersUseCase: GetUsersUseCase,
    val loginUseCase: LoginUseCase,
) : ViewModel() {

    private val _users = MutableStateFlow<Resource<Users>>(Resource.Loading())
    val users: StateFlow<Resource<Users>> get() = _users

    private val _loginResult = MutableStateFlow<Resource<Boolean>>(Resource.Loading())
    val loginResult: StateFlow<Resource<Boolean>> get() = _loginResult

    private val _loginState = MutableStateFlow<Resource<LoginResponse>>(Resource.Loading())
    val loginState: StateFlow<Resource<LoginResponse>> get() = _loginState

    init {
        getUsers()
    }

    private fun getUsers() {
        viewModelScope.launch {
            getUsersUseCase()
                .collect { result ->
                    _users.value = result
                }
        }
    }

    fun login(username: String, password: String) {
        loginUseCase(LoginRequest(username, password)).onEach {
            when (it) {
                is Resource.Loading -> {
                    _loginState.value = Resource.Loading()
                }

                is Resource.Success -> {
                    _loginState.value = Resource.Success(it.data!!)
                    Log.d("TOKEN",it.data.token)
                }

                is Resource.Error -> {
                    _loginState.value = Resource.Error(it.errorMessage)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun validateCredentials(username: String, password: String) {
        viewModelScope.launch {
            when (val result = _users.value) {
                is Resource.Success -> {
                    val isValid = result.data?.users?.any { it.username == username && it.password == password } == true
                    _loginResult.value = Resource.Success(isValid)
                }

                else -> {
                    _loginResult.value = Resource.Error("Validation failed")
                }
            }
        }
    }
}