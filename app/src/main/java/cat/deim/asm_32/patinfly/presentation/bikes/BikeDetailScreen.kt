package cat.deim.asm_32.patinfly.presentation.bikes

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import cat.deim.asm_32.patinfly.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BikeDetailScreen(
    viewModel: BikeDetailViewModel,
    modifier: Modifier = Modifier
) {
    val bike by viewModel.bike.collectAsState()

    val context = LocalContext.current
    val activity = context as? Activity
    val sharedPrefs = context.getSharedPreferences("session", Context.MODE_PRIVATE)
    val userId = sharedPrefs.getString("uuid", "") ?: ""
    Log.d("BikeDetailScreen", "uuid: $userId")
    bike?.let { bike ->
        val statusText = when {
            bike.isReserved && bike.userId== userId -> "Reservada per mi"
            bike.isReserved && bike.userId !=userId -> "Algú altre ja ha fet la reserva"
            bike.isRented && bike.userId==userId -> "Llogada per mi"
            bike.isRented && bike.userId != userId -> "Algú altre ja l'ha llogat"
            else -> "Disponible"
        }
        val canReserve = !bike.isDisabled && (!bike.isReserved || bike.userId==userId) && !bike.isRented
        val canRent = (!bike.isDisabled && !bike.isRented && (!bike.isReserved || bike.userId==userId))

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = stringResource(R.string.bike_detail_title)) },
                    navigationIcon = {
                        IconButton(onClick = { activity?.finish() }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_arrow_back),
                                contentDescription = stringResource(R.string.back)
                            )
                        }
                    }
                )
            }
        ) { paddingValues ->
            Column(
                modifier = modifier
                    .padding(paddingValues)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Image(
                    painter = painterResource(id = R.drawable.bike_card),
                    contentDescription = stringResource(R.string.bike_image_desc),
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                )

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = bike.name,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(text = "id: ${bike.uuid}")
                Text(text = "Tipus bici: ${bike.typeUuid}")
                Text(text = "Latitud: ${bike.lat}")
                Text(text = "Longitud: ${bike.lon}")
                Text(text = "Estat: $statusText")

                Spacer(modifier = Modifier.height(8.dp))

                BikeMapScreen(
                    lat = bike.lat,
                    lon = bike.lon,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .clip(RoundedCornerShape(16.dp))
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Botón reservar / cancelar reserva
                val isMyReservation = bike.isReserved && bike.userId == userId

                Button(
                    onClick = {
                        viewModel.toggleReservation(userId)
                    },
                    enabled = canReserve,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = when {
                            !canReserve -> Color.LightGray
                            isMyReservation -> Color(0xFFFFEB3B)
                            else -> Color(0xFF448AFF)
                        },
                        contentColor = Color.White
                    )
                ) {
                    Text(if (bike.isReserved && bike.userId == userId) "Cancelar reserva" else "Reservar")
                }

                Spacer(modifier = Modifier.height(16.dp))

                val isRentedByUser = bike.isRented && bike.userId == userId

                Button(
                    onClick = {
                        val intent = Intent(context, BikeRentDetailActivity::class.java).apply {
                            putExtra("bike_uuid", bike.uuid)
                            putExtra("user_id", userId) // si necesitas pasar el userId
                        }
                        context.startActivity(intent)
                    },
                    enabled = canRent || isRentedByUser,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = when {
                            isRentedByUser -> Color.Red
                            canRent -> Color(0xFF2E7D32)
                            else -> Color.LightGray
                        },
                        contentColor = Color.White
                    )
                ) {
                    Text(if (isRentedByUser) "Aturar lloguer" else "Llogar")
                }
            }
        }
    }
}