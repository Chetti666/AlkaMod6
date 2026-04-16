package com.example.alkamod6.data.local.dao

import androidx.room.*
import com.example.alkamod6.data.local.entities.TransactionEntity
import com.example.alkamod6.data.local.entities.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WalletDao {
    // Transacciones
    @Query("SELECT * FROM transactions ORDER BY date DESC")
    fun getAllTransactions(): Flow<List<TransactionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransactions(transactions: List<TransactionEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: TransactionEntity)

    // Perfil de Usuario
    @Query("SELECT * FROM user_profile LIMIT 1")
    fun getUserProfile(): Flow<UserEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUserProfile(user: UserEntity)

    @Query("DELETE FROM user_profile")
    suspend fun clearUserProfile()
}
