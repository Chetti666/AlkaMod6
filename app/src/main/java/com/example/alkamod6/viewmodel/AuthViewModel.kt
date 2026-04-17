package com.example.alkamod6.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alkamod6.data.local.entities.UserEntity
import com.example.alkamod6.data.model.LoginRequest
import com.example.alkamod6.data.model.RegisterRequest
import com.example.alkamod6.data.repository.WalletRepository
import kotlinx.coroutines.launch

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val user: UserEntity) : AuthState()
    data class Error(val message: String) : AuthState()
}

class AuthViewModel(private val repository: WalletRepository) : ViewModel() {

    var authState by mutableStateOf<AuthState>(AuthState.Idle)
        private set

    fun login(email: String, pass: String) {
        viewModelScope.launch {
            authState = AuthState.Loading
            try {
                // Para MockAPI simulamos el login buscando al usuario en la lista
                val response = repository.loginMock(email, pass)
                if (response != null) {
                    repository.saveUserProfile(response)
                    authState = AuthState.Success(response)
                } else {
                    authState = AuthState.Error("Credenciales inválidas o usuario no encontrado")
                }
            } catch (e: Exception) {
                authState = AuthState.Error("Error de conexión: ${e.message}")
            }
        }
    }

    fun register(name: String, email: String, pass: String) {
        viewModelScope.launch {
            authState = AuthState.Loading
            try {
                val request = RegisterRequest(name, email, pass)
                val response = repository.register(request)
                if (response.isSuccessful) {
                    authState = AuthState.Idle // Redirigir al login
                } else {
                    authState = AuthState.Error("Error al registrar: ${response.code()}")
                }
            } catch (e: Exception) {
                authState = AuthState.Error("Error de conexión: ${e.message}")
            }
        }
    }
}
