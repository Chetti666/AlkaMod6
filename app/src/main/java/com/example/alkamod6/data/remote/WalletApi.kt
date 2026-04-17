package com.example.alkamod6.data.remote

import com.example.alkamod6.data.model.*
import retrofit2.Response
import retrofit2.http.*

interface WalletApi {
    
    // Para MockAPI, el login suele ser un GET a /users filtrando por email
    @GET("users")
    suspend fun getUsers(): Response<List<UserResponse>>

    @POST("users")
    suspend fun register(@Body request: RegisterRequest): Response<Unit>

    @GET("transactions")
    suspend fun getTransactions(): Response<List<TransactionResponse>>

    @POST("transactions")
    suspend fun createTransaction(@Body transaction: TransactionResponse): Response<Unit>
}
