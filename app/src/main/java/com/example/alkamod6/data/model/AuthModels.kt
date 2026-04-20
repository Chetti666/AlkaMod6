package com.example.alkamod6.data.model

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse(
    val token: String?,
    val user: UserResponse?
)

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String
)

data class UserResponse(
    val id: String?,
    val name: String?,
    val email: String?,
    val password: String?,
    @SerializedName("avatarUrl") val avatarUrl: String?,
    val balance: Double?
)

data class TransactionResponse(
    val id: String?,
    val amount: Double?,
    val description: String?,
    val date: String?,
    val type: String?
)
