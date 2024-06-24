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
import com.example.eray_altilar_final.presentation.ui.login.LoginViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserByTokenUseCase: GetUserByTokenUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileScreenUIState())
    val uiState: StateFlow<ProfileScreenUIState> = _uiState.asStateFlow()

    init {
        val token = getToken()
        getUserByToken(token)
    }

    fun updateUser(id: Long, userUpdateRequest: UserUpdateRequest) {
        updateUserUseCase(id, userUpdateRequest).onEach {
            when (it) {
                is Resource.Loading -> {
                    _uiState.update { state ->
                        state.copy(
                            loadingState = true,
                            isHaveError = false,
                            isSuccess = false,
                        )
                    }
                }

                is Resource.Success -> {
                    _uiState.update { state ->
                        state.copy(
                            isSuccess = true,
                            loadingState = false,
                            isHaveError = false,
                            user = it.data, 
                        )
                    }
                }

                is Resource.Error -> {
                    _uiState.update { state ->
                        state.copy(
                            loadingState = false,
                            isHaveError = true,
                            errorMessage = it.message.orEmpty(),
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getUserByToken(token: String?) {
        getUserByTokenUseCase(token!!).onEach {
            when (it) {
                is Resource.Loading -> {
                    _uiState.update { state ->
                        state.copy(
                            loadingState = true,
                            isHaveError = false,
                            isSuccess = false,
                        )
                    }
                }

                is Resource.Success -> {
                    _uiState.update { state ->
                        state.copy(
                            loadingState = false,
                            isHaveError = false,
                            isSuccess = true,
                            user = it.data,
                        )
                    }
                }

                is Resource.Error -> {
                    _uiState.update { state ->
                        state.copy(
                            loadingState = false,
                            isHaveError = true,
                            errorMessage = it.message.orEmpty(),
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    data class ProfileScreenUIState(
        val loadingState: Boolean = false,
        val isHaveError: Boolean = false,
        val isSuccess: Boolean = false,
        val errorMessage: String = "",
        val user: User? = null,
    )
}