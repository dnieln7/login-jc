package com.dnieln7.login.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dnieln7.login.domain.User

@Composable
fun UserDetails(user: User) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "User Details", style = MaterialTheme.typography.h6)
        Spacer(modifier = Modifier.height(20.dp))
        InfoRow(label = "Name", text = user.name)
        InfoRow(label = "Last Name", text = user.lastName)
        InfoRow(label = "Username", text = user.username)
        InfoRow(label = "Email", text = user.email)
    }
}

@Composable
fun InfoRow(label: String, text: String) {
    Row(
        modifier = Modifier.padding(vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "$label :", style = MaterialTheme.typography.caption)
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = text, style = MaterialTheme.typography.body1)
    }
}

data class DetailsState(
    val user: User? = null,
    val isLoading: Boolean = false,
)
