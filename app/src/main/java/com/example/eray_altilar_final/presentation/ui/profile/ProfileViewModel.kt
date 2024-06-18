package com.example.eray_altilar_final.presentation.ui.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eray_altilar_final.core.Resource
import com.example.eray_altilar_final.core.SharedPreferencesManager.getUserId
import com.example.eray_altilar_final.domain.model.usermodel.User
import com.example.eray_altilar_final.domain.model.usermodel.Users
import com.example.eray_altilar_final.domain.usecase.GetUserByIdUseCase
import com.example.eray_altilar_final.domain.usecase.GetUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserByIdUseCase: GetUserByIdUseCase,
) : ViewModel() {

    private val _user = MutableStateFlow<Resource<User>>(Resource.Loading())
    val user: StateFlow<Resource<User>> get() = _user

    init {
        val userId = getUserId()
        getUser(userId)
        Log.d("UserID",  "init: $userId")
    }

    private fun getUser(userId: Long) {
        viewModelScope.launch {
            getUserByIdUseCase(userId)
                .collect { result ->
                    _user.value = result
                }
        }
    }
}