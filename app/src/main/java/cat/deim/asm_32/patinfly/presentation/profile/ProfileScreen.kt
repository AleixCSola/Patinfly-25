package cat.deim.asm_32.patinfly.presentation.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cat.deim.asm_32.patinfly.data.datasource.model.UserModel

@Composable
fun ProfileScreen(user: UserModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("User Profile", style = MaterialTheme.typography.headlineMedium)
        Text("Name: ${user.name}")
        Text("Email: ${user.email}")
        Text("UUID: ${user.uuid}")
        Text("Device ID: ${user.deviceId}")
        Text("Created: ${user.creationDate}")
        Text("Last Connection: ${user.lastConnection}")
    }
}