package com.example.alkamod6.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alkamod6.data.local.entities.UserEntity
import com.example.alkamod6.data.model.LoginRequest
import com.example.alkamod6.data.model.RegisterRequest
import com.example.alkamod6.data.repository.WalletRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val token: String) : AuthState()
    data class Error(val message: String) : AuthState()
}

class AuthViewModel(private val repository: WalletRepository) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    fun login(email: String, pass: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                val response = repository.login(LoginRequest(email, pass))
                if (response.isSuccessful && response.body() != null) {
                    val loginResponse = response.body()!!
                    // Guardar perfil localmente
                    repository.saveUserProfile(
                        UserEntity(
                            id = loginResponse.user.id,
                            name = loginResponse.user.name,
                            email = loginResponse.user.email,
                            avatarUrl = loginResponse.user.avatarUrl,
                            balance = loginResponse.user.balance,
                            token = loginResponse.token
                        )
                    )
                    _authState.value = AuthState.Success(loginResponse.token)
                } else {
                    _authState.value = AuthState.Error("Error en credenciales")
                }
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Error de red")
            }
        }
    }

    fun register(name: String, email: String, pass: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                val response = repository.register(RegisterRequest(name, email, pass))
                if (response.isSuccessful) {
                    _authState.value = AuthState.Idle // O navegar al login
                } else {
                    _authState.value = AuthState.Error("Error al registrar")
                }
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Error de red")
            }
        }
    }
}
