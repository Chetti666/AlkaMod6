package com.example.alkamod6.data.model

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse(
    val token: String,
    val user: UserDto
)

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String
)

data class UserDto(
    val id: Int,
    val name: String,
    val email: String,
    @SerializedName("avatar_url") val avatarUrl: String,
    val balance: Double
)

data class TransactionResponse(
    val id: Int,
    val amount: Double,
    val description: String,
    val date: String,
    val type: String
)
