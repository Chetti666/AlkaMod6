package com.example.alkamod6.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.alkamod6.ui.screens.*
import com.example.alkamod6.viewmodel.AuthState
import com.example.alkamod6.viewmodel.AuthViewModel
import com.example.alkamod6.viewmodel.TransactionViewModel

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
    object SendMoney : Screen("send_money") // Nueva ruta
}

@Composable
fun WalletNavGraph(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    modifier: Modifier = Modifier,
    transactionViewModel: TransactionViewModel
) {
    val authState = authViewModel.authState

    LaunchedEffect(authState) {
        if (authState is AuthState.Success) {
            navController.navigate(Screen.Home.route) {
                popUpTo(Screen.Login.route) { inclusive = true }
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route,
        modifier = modifier
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                authState = authState,
                onLoginClick = { email, pass -> authViewModel.login(email, pass) },
                onRegisterClick = { navController.navigate(Screen.Register.route) }
            )
        }
        composable(Screen.Register.route) {
            RegisterScreen(
                onRegisterClick = { name, email, pass -> authViewModel.register(name, email, pass) },
                onBackToLogin = { navController.popBackStack() }
            )
        }
        composable(Screen.Home.route) {
            val user by transactionViewModel.userProfile.collectAsState()
            val transactions by transactionViewModel.transactions.collectAsState()

            LaunchedEffect(Unit) { transactionViewModel.loadData() }

            DashboardScreen(
                user = user,
                transactions = transactions,
                onSendClick = { navController.navigate(Screen.SendMoney.route) } // Acción del botón
            )
        }
        composable(Screen.SendMoney.route) {
            SendMoneyScreen(
                onSendClick = { amount, desc ->
                    // Lógica para enviar dinero (ViewModel)
                    // Por ahora solo volvemos atrás
                    navController.popBackStack()
                },
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
