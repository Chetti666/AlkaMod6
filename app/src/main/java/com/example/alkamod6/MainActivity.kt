package com.example.alkamod6

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.alkamod6.data.local.AppDatabase
import com.example.alkamod6.data.remote.WalletApi
import com.example.alkamod6.data.repository.WalletRepository
import com.example.alkamod6.ui.navigation.WalletNavGraph
import com.example.alkamod6.ui.theme.AlkaMod6Theme
import com.example.alkamod6.viewmodel.AuthViewModel
import com.example.alkamod6.viewmodel.TransactionViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "wallet-db"
        ).build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://69e29f813327837a15528719.mockapi.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(WalletApi::class.java)
        val repository = WalletRepository(api, db.walletDao())
        
        val authViewModel = AuthViewModel(repository)
        val transactionViewModel = TransactionViewModel(repository)

        enableEdgeToEdge()
        setContent {
            AlkaMod6Theme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    WalletNavGraph(
                        navController = navController,
                        authViewModel = authViewModel,
                        transactionViewModel = transactionViewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
