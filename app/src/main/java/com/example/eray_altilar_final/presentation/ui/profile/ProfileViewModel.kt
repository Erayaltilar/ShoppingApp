package com.example.eray_altilar_final.presentation.ui.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eray_altilar_final.core.Resource
import com.example.eray_altilar_final.core.SharedPreferencesManager.getToken
import com.example.eray_altilar_final.domain.model.usermodel.User
import com.example.eray_altilar_final.domain.model.usermodel.UserUpdateRequest
import com.example.eray_altilar_final.domain.usecase.user.GetUserByIdUseCase
import com.example.eray_altilar_final.domain.usecase.user.GetUserByTokenUseCase
import com.example.eray_altilar_final.domain.usecase.user.UpdateUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val getUserByTokenUseCase: GetUserByTokenUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
) : ViewModel() {

    private val _user = MutableStateFlow<Resource<User>>(Resource.Loading())
    val user: StateFlow<Resource<User>> get() = _user

    init {
        val token = getToken()
        getUserByToken(token)


    }

    fun updateUser(id: Long, userUpdateRequest: UserUpdateRequest) {
        updateUserUseCase(id, userUpdateRequest).onEach {
            when (it) {
                is Resource.Loading -> {
                    _user.value = Resource.Loading()
                }

                is Resource.Success -> {
                    _user.value = Resource.Success(it.data!!)
                }

                is Resource.Error -> {
                    _user.value = Resource.Error(it.message)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getUserByToken(token: String?) {
        getUserByTokenUseCase(token!!).onEach {
            when (it) {
                is Resource.Loading -> {
                    _user.value = Resource.Loading()
                }

                is Resource.Success -> {
                    _user.value = Resource.Success(it.data!!)
                    Log.d("CurrentUserEmail", "getUser: ${it.data}")
                }

                is Resource.Error -> {
                    _user.value = Resource.Error(it.message)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getUser(userId: Long) {
        viewModelScope.launch {
            getUserByIdUseCase(userId)
                .collect { result ->
                    _user.value = result
                    Log.d("User", "getUser: ${result.data?.email}")
                }
        }
    }
}