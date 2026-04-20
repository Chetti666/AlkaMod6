package com.example.alkamod6.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alkamod6.data.local.entities.TransactionEntity
import com.example.alkamod6.data.local.entities.UserEntity
import com.example.alkamod6.data.repository.WalletRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TransactionViewModel(private val repository: WalletRepository) : ViewModel() {

    // Observa el perfil del usuario desde el repositorio (Req 4 y 5)
    val userProfile: StateFlow<UserEntity?> = repository.getUserProfile()
        .stateIn(viewModelScope, SharingStarted.Lazily, null)

    // Observa las transacciones locales (Req 2 y 4)
    // Para este ejemplo, añadiremos un método en el repositorio para obtener el flujo
    // Pero por ahora lo simularemos directamente si el repositorio lo permite o ajustamos el repo
    val transactions: StateFlow<List<TransactionEntity>> = repository.getAllTransactions()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun loadData() {
        viewModelScope.launch {
            try {
                val response = repository.fetchTransactions()
                if (response.isSuccessful && response.body() != null) {
                    val remoteData = response.body()!!.map { 
                        TransactionEntity(
                            amount = it.amount ?: 0.0,
                            description = it.description ?: "",
                            date = it.date ?: "",
                            type = it.type ?: "SEND"
                        )
                    }
                    // Guardar localmente a través del repositorio
                    remoteData.forEach { repository.insertTransactionLocal(it) }
                }
            } catch (e: Exception) {
                // Manejo de errores
            }
        }
    }
}
