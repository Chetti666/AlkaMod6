package com.example.alkamod6.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.alkamod6.ui.screens.DashboardScreen
import com.example.alkamod6.ui.screens.LoginScreen
import com.example.alkamod6.ui.screens.RegisterScreen
import com.example.alkamod6.viewmodel.AuthState
import com.example.alkamod6.viewmodel.AuthViewModel
import com.example.alkamod6.viewmodel.TransactionViewModel

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
}

@Composable
fun WalletNavGraph(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    modifier: Modifier = Modifier,
    transactionViewModel: TransactionViewModel // Añadido
) {
    // Observar el estado de autenticación
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
                onLoginClick = { email, pass -> 
                    authViewModel.login(email, pass)
                },
                onRegisterClick = { 
                    navController.navigate(Screen.Register.route)
                }
            )
        }
        composable(Screen.Register.route) {
            RegisterScreen(
                onRegisterClick = { name, email, pass ->
                    authViewModel.register(name, email, pass)
                },
                onBackToLogin = {
                    navController.popBackStack()
                }
            )
        }
        composable(Screen.Home.route) {
            val user by transactionViewModel.userProfile.collectAsState()
            val transactions by transactionViewModel.transactions.collectAsState()

            LaunchedEffect(Unit) {
                transactionViewModel.loadData()
            }

            DashboardScreen(
                user = user,
                transactions = transactions,
                onSendClick = {
                    // Aquí iría la navegación a enviar dinero
                }
            )
        }
    }
}
