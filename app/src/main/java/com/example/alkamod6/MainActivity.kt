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
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Inicialización básica (En un proyecto real usaríamos Hilt/Koin)
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "wallet-db"
        ).build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://tu-api-ejemplo.com/api/") // Cambiar por la URL real de la API
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(WalletApi::class.java)
        val repository = WalletRepository(api, db.walletDao())
        val authViewModel = AuthViewModel(repository)

        enableEdgeToEdge()
        setContent {
            AlkaMod6Theme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    WalletNavGraph(
                        navController = navController,
                        authViewModel = authViewModel
                    )
                }
            }
        }
    }
}
