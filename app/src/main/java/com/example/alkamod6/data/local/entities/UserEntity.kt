package com.example.alkamod6.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profile")
data class UserEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val email: String,
    val avatarUrl: String, // Para cargar con Coil (Req 5)
    val balance: Double,
    val token: String // Para gestión de sesiones (Req 1)
)
