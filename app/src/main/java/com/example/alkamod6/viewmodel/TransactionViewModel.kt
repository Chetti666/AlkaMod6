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

    // Observa el perfil del usuario desde Room (Req 4 y 5)
    val userProfile: StateFlow<UserEntity?> = repository.getUserProfile()
        .stateIn(viewModelScope, SharingStarted.Lazily, null)

    // Observa las transacciones locales (Req 2 y 4)
    // En un caso real, aquí llamaríamos a fetchTransactions de la API primero
    val transactions: StateFlow<List<TransactionEntity>> = repository.dao.getAllTransactions()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun loadData(token: String) {
        viewModelScope.launch {
            try {
                val response = repository.fetchTransactions(token)
                if (response.isSuccessful && response.body() != null) {
                    val remoteData = response.body()!!.map { 
                        TransactionEntity(
                            amount = it.amount,
                            description = it.description,
                            date = it.date,
                            type = it.type
                        )
                    }
                    repository.dao.insertTransactions(remoteData)
                }
            } catch (e: Exception) {
                // Manejo de errores (Req: Manejo de errores adecuado)
            }
        }
    }
}
