package com.example.eray_altilar_final.presentation.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eray_altilar_final.core.Resource
import com.example.eray_altilar_final.core.SharedPreferencesManager.saveToken
import com.example.eray_altilar_final.core.SharedPreferencesManager.saveUserId
import com.example.eray_altilar_final.domain.model.LoginRequest
import com.example.eray_altilar_final.domain.model.usermodel.User
import com.example.eray_altilar_final.domain.usecase.user.GetUserByTokenUseCase
import com.example.eray_altilar_final.domain.usecase.user.GetUsersUseCase
import com.example.eray_altilar_final.domain.usecase.user.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    val loginUseCase: LoginUseCase,
    val getUserByTokenUseCase: GetUserByTokenUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginScreenUIState())
    val uiState: StateFlow<LoginScreenUIState> = _uiState.asStateFlow()

    fun getToken(username: String, password: String) {
        loginUseCase(LoginRequest(username, password)).onEach {
            when (it) {
                is Resource.Loading -> {}

                is Resource.Success -> {
                    _uiState.update { state ->
                        state.copy(
                            loadingState = false,
                            tokenGet = true,
                            token = it.data?.token ?: "",
                            isSuccess = true,
                        )
                    }
                    saveToken(it.data?.token ?: "")
                    it.data?.user?.let { user ->
                        saveUserId(user.id)
                    }
                    getUserByToken(it.data?.token ?: "")
                }

                is Resource.Error -> {
                    _uiState.update { state ->
                        state.copy(isHaveError = true, errorMessage = it.message ?: "")
                    }
                    Log.e("tokenVM ERROR", it.message ?: "")
                }

            }
        }.launchIn(viewModelScope)
    }

    private fun getUserByToken(token: String) {
        getUserByTokenUseCase(token).onEach {
            when (it) {
                is Resource.Loading -> {
                    _uiState.update { state ->
                        state.copy(loadingState = true)
                    }
                }

                is Resource.Success -> {
                    _uiState.update { state ->
                        state.copy(
                            loadingState = false,
                            isLoginSuccess = true,
                            loggedUser = it.data,
                        )
                    }
                    it.data?.let { user ->
                        saveUserId(user.id)
                    }
                }

                is Resource.Error -> {
                    _uiState.update { state ->
                        state.copy(isHaveError = true, errorMessage = it.message ?: "")
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    data class LoginScreenUIState(
        val loadingState: Boolean = false,
        val isHaveError: Boolean = false,
        val isSuccess: Boolean = false,
        val errorMessage: String = "",
        val token: String = "",
        val tokenGet: Boolean = false,
        val loggedUser: User? = null,
        val isLoginSuccess: Boolean = false,
        val users: List<User> = emptyList()
    )
}