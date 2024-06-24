package com.example.eray_altilar_final.presentation.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eray_altilar_final.R
import com.example.eray_altilar_final.core.Resource
import com.example.eray_altilar_final.core.SharedPreferencesManager.saveToken
import com.example.eray_altilar_final.core.SharedPreferencesManager.saveUserId
import com.example.eray_altilar_final.domain.model.LoginRequest
import com.example.eray_altilar_final.domain.model.usermodel.User
import com.example.eray_altilar_final.domain.usecase.remote_config.FetchRemoteConfigUseCase
import com.example.eray_altilar_final.domain.usecase.remote_config.GetRemoteConfigValueUseCase
import com.example.eray_altilar_final.domain.usecase.user.GetUserByTokenUseCase
import com.example.eray_altilar_final.domain.usecase.user.LoginUseCase
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
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
    val fetchRemoteConfigUseCase: FetchRemoteConfigUseCase,
    val getRemoteConfigValueUseCase: GetRemoteConfigValueUseCase,
    private val remoteConfig: FirebaseRemoteConfig,
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginScreenUIState())
    val uiState: StateFlow<LoginScreenUIState> = _uiState.asStateFlow()

    init {
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)

        fetchRemoteConfig()
    }

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
                        state.copy(isHaveError = true, errorMessage = it.errorMessage.orEmpty())
                    }
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
                        state.copy(isHaveError = true, errorMessage = it.errorMessage.orEmpty())
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun fetchRemoteConfig() {
        fetchRemoteConfigUseCase().onEach {
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
                            isFetchSuccessful = true,
                        )
                    }
                }

                is Resource.Error -> {
                    _uiState.update { state ->
                        state.copy(
                            isHaveError = true,
                            errorMessage = it.errorMessage.orEmpty(),
                            loadingState = false,
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getRemoteConfig(key: String) {
        getRemoteConfigValueUseCase(key).onEach {
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
                            backgroundColor = it.data ?: "",
                        )
                    }
                }

                is Resource.Error -> {
                    _uiState.update { state ->
                        state.copy(
                            isHaveError = true,
                            loadingState = false,
                            errorMessage = it.errorMessage.orEmpty(),
                        )
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
        val users: List<User> = emptyList(),
        val isFetchSuccessful: Boolean = false,
        val backgroundColor: String = "#000000",
    )
}