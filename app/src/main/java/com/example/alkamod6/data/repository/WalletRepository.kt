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
    suspend fun login(request: LoginRequest): Response<LoginResponse> {
        // En una implementación real se llamaría a api.login
        // Pero para MockAPI simulamos el login buscando en la lista de usuarios
        return Response.success(null) // Esto es solo un placeholder
    }

    suspend fun loginMock(email: String, pass: String): UserEntity? {
        val response = api.getUsers()
        if (response.isSuccessful && response.body() != null) {
            val user = response.body()?.find { it.email == email && it.password == pass }
            return user?.let {
                UserEntity(
                    id = it.id.toIntOrNull() ?: 0,
                    name = it.name,
                    email = it.email,
                    avatarUrl = it.avatarUrl,
                    balance = it.balance,
                    token = "mock-token-${it.id}"
                )
            }
        }
        return null
    }
    
    suspend fun register(request: RegisterRequest): Response<Unit> = api.register(request)

    // Perfil Local
    fun getUserProfile(): Flow<UserEntity?> = dao.getUserProfile()

    suspend fun saveUserProfile(user: UserEntity) = dao.saveUserProfile(user)

    // Transacciones
    fun getAllTransactions(): Flow<List<TransactionEntity>> = dao.getAllTransactions()

    suspend fun fetchTransactions(): Response<List<TransactionResponse>> {
        return api.getTransactions()
    }

    suspend fun createTransaction(transaction: TransactionResponse): Response<Unit> {
        return api.createTransaction(transaction)
    }

    suspend fun insertTransactionLocal(transaction: TransactionEntity) {
        dao.insertTransaction(transaction)
    }
}
