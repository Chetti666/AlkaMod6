package com.example.alkamod6.data.remote

import com.example.alkamod6.data.model.LoginRequest
import com.example.alkamod6.data.model.LoginResponse
import com.example.alkamod6.data.model.RegisterRequest
import com.example.alkamod6.data.model.TransactionResponse
import retrofit2.Response
import retrofit2.http.*

interface WalletApi {
    @POST("login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("users")
    suspend fun register(@Body request: RegisterRequest): Response<Unit>

    @GET("transactions")
    suspend fun getTransactions(@Header("Authorization") token: String): Response<List<TransactionResponse>>

    @POST("transactions")
    suspend fun createTransaction(
        @Header("Authorization") token: String,
        @Body transaction: TransactionResponse
    ): Response<Unit>
}
