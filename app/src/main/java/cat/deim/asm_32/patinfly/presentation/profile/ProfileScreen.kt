package cat.deim.asm_32.patinfly.presentation.profile

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import cat.deim.asm_32.patinfly.R
import cat.deim.asm_32.patinfly.data.datasource.model.UserModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(usuari: UserModel) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Back") },
                navigationIcon = {
                    IconButton(onClick = {
                        ActivityCompat.finishAfterTransition(context as ComponentActivity)
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_back),
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text("Perfil", style = MaterialTheme.typography.headlineMedium)

                    Row(
                        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Column {
                            Text(usuari.name, style = MaterialTheme.typography.bodyLarge)
                            Text(usuari.email, style = MaterialTheme.typography.bodySmall)
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("UUID: ${usuari.uuid}")
                    Text("Device ID: ${usuari.deviceId}")
                    Text("Created: ${usuari.creationDate}")
                    Text("Last Connection: ${usuari.lastConnection}")
                }
            }
        }
    }
}