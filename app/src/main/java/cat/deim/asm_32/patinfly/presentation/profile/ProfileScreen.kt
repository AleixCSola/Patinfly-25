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
                title = { Text("Perfil") },
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
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = usuari.name,
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = usuari.email,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(16.dp))
                    DetallesText("UUID:", usuari.uuid)
                    DetallesText("Device ID:", usuari.deviceId)
                    DetallesText("Created:", usuari.creationDate.toString())
                    DetallesText("Last Connection:", usuari.lastConnection.toString())
                }
            }
        }
    }
}

@Composable
fun DetallesText(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = value)
    }
}