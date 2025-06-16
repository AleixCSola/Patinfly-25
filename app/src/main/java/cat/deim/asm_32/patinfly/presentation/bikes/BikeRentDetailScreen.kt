package cat.deim.asm_32.patinfly.presentation.bikes

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import cat.deim.asm_32.patinfly.R
import cat.deim.asm_32.patinfly.presentation.main.MainActivity
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BikeRentDetailScreen(viewModel: BikeRentDetailViewModel) {
    val rentToggled by viewModel.rentToggled.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val isRentedByUser by viewModel.isRentedByUser.collectAsState()
    val bike by viewModel.bike.collectAsState()
    val pricingPlan by viewModel.pricingPlan.collectAsState()
    val context = LocalContext.current
    val activity = context as? Activity
    LaunchedEffect(rentToggled) {
        if (rentToggled==true) {
            Toast.makeText(context, "Acció completada", Toast.LENGTH_SHORT).show()
            delay(500)
            activity?.finish()

            val intent = Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            }
            context.startActivity(intent)

        } else if (rentToggled == false) {
            Toast.makeText(context, "Error en l'acció", Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalls del lloguer") },
                navigationIcon = {
                    IconButton(onClick = { activity?.finish() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_back),
                            contentDescription = "Tornar enrere"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            bike?.let { b ->
                Image(
                    painter = painterResource(id = R.drawable.bike_card), // O tu imagen real
                    contentDescription = "Foto de la bici",
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "id: ${b.uuid}")
                Text(text = "Tipus bici: ${b.typeUuid}")
                Text(text = "Latitud: ${b.lat}")
                Text(text = "Longitud: ${b.lon}")

                Spacer(modifier = Modifier.height(8.dp))

                BikeMapScreen(
                    lat = bike!!.lat,
                    lon = bike!!.lon,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .clip(RoundedCornerShape(16.dp))
                )

                Spacer(modifier = Modifier.height(16.dp))
            }

            pricingPlan?.let { plan ->
                Spacer(modifier = Modifier.height(16.dp))
                Text("Pla de preus: ${plan.dataPlan.name.text}")
                Text("Descripció: ${plan.dataPlan.description.text}")
            }

            Button(
                onClick = { viewModel.toggleRent() },
                enabled = !isLoading,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isRentedByUser) Color.Red else Color(0xFF2E7D32),
                    contentColor = Color.White
                )
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        color = Color.White,
                        strokeWidth = 2.dp,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
                Text(if (isRentedByUser) "Confirmar aturar lloguer" else "Confirmar iniciar lloguer")
            }
        }
    }
}