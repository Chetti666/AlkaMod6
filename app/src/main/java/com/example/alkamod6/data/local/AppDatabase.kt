package com.example.alkamod6.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.alkamod6.data.local.dao.WalletDao
import com.example.alkamod6.data.local.entities.TransactionEntity
import com.example.alkamod6.data.local.entities.UserEntity

@Database(entities = [TransactionEntity::class, UserEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun walletDao(): WalletDao
}
