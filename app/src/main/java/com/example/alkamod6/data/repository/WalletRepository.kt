package com.example.alkamod6.data.repository

import com.example.alkamod6.data.local.dao.WalletDao
import com.example.alkamod6.data.local.entities.TransactionEntity
import com.example.alkamod6.data.local.entities.UserEntity
import com.example.alkamod6.data.model.*
import com.example.alkamod6.data.remote.WalletApi
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class WalletRepository(
    private val api: WalletApi,
    private val dao: WalletDao
) {
    // Auth
    suspend fun login(request: LoginRequest): Response<LoginResponse> = api.login(request)
    
    suspend fun register(request: RegisterRequest): Response<Unit> = api.register(request)

    // Perfil Local
    fun getUserProfile(): Flow<UserEntity?> = dao.getUserProfile()

    suspend fun saveUserProfile(user: UserEntity) = dao.saveUserProfile(user)

    // Transacciones
    fun getAllTransactions(): Flow<List<TransactionEntity>> = dao.getAllTransactions()

    suspend fun fetchTransactions(token: String): Response<List<TransactionResponse>> {
        return api.getTransactions("Bearer $token")
    }

    suspend fun createTransaction(token: String, transaction: TransactionResponse): Response<Unit> {
        return api.createTransaction("Bearer $token", transaction)
    }

    suspend fun insertTransactionLocal(transaction: TransactionEntity) {
        dao.insertTransaction(transaction)
    }
}
