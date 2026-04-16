package com.example.alkamod6.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.alkamod6.data.local.entities.TransactionEntity
import com.example.alkamod6.data.local.entities.UserEntity

@Composable
fun DashboardScreen(
    user: UserEntity?,
    transactions: List<TransactionEntity>,
    onSendClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Cabecera: Perfil (Req 5 - Carga de imágenes con Coil)
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = user?.avatarUrl ?: "https://via.placeholder.com/150",
                contentDescription = "Avatar",
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = "Hola,", fontSize = 14.sp)
                Text(text = user?.name ?: "Usuario", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Saldo
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text(text = "Tu saldo disponible", fontSize = 14.sp)
                Text(
                    text = "$${user?.balance ?: 0.0}",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Acciones
        Button(
            onClick = onSendClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Enviar Dinero")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Historial (Req 2)
        Text(text = "Historial de Transacciones", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(transactions) { transaction ->
                TransactionItem(transaction)
            }
        }
    }
}

@Composable
fun TransactionItem(transaction: TransactionEntity) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = transaction.description, fontWeight = FontWeight.SemiBold)
                Text(text = transaction.date, fontSize = 12.sp, color = Color.Gray)
            }
            Text(
                text = "${if (transaction.type == "SEND") "-" else "+"}$${transaction.amount}",
                color = if (transaction.type == "SEND") Color.Red else Color.Green,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
