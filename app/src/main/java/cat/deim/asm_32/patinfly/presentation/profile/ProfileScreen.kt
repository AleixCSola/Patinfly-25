package cat.deim.asm_32.patinfly.presentation.profile

import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.core.app.ActivityCompat
import cat.deim.asm_32.patinfly.R
import cat.deim.asm_32.patinfly.data.datasource.remote.RentalResponse
import cat.deim.asm_32.patinfly.domain.models.User
import java.time.Duration
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.time.LocalDateTime
import java.time.ZoneOffset

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(usuari: User, rentalHistory: List<RentalResponse>) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.profile)) },
                navigationIcon = {
                    IconButton(onClick = {
                        ActivityCompat.finishAfterTransition(context as ComponentActivity)
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_back),
                            contentDescription = stringResource(R.string.back)
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
                .padding(dimensionResource(R.dimen.padding_medium))
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(dimensionResource(R.dimen.profile_card_corner_radius)),
                elevation = CardDefaults.cardElevation(dimensionResource(R.dimen.profile_card_elevation))
            ) {
                Column(
                    modifier = Modifier
                        .padding(dimensionResource(R.dimen.padding_medium))
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.profile_picture),
                            contentDescription = stringResource(R.string.profile_picture_desc),
                            modifier = Modifier
                                .size(dimensionResource(R.dimen.profile_image_size))
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                        Column {
                            Text(
                                text = usuari.name,
                                style = MaterialTheme.typography.headlineSmall.copy(
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_extra_small)))
                            Text(
                                text = usuari.email,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                    HorizontalDivider()
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = dimensionResource(R.dimen.padding_small)),
                        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_medium))
                    ) {
                        DetallesText(stringResource(R.string.uuid_label), usuari.uuid)
                        DetallesText(stringResource(R.string.device_id_label), usuari.deviceId)
                        DetallesText(stringResource(R.string.created_label), usuari.creationDate.toString())
                        DetallesText(stringResource(R.string.last_connection_label), usuari.lastConnection.toString())
                    }
                }
            }
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_long)))

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Historial d'alquilers",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))

                rentalHistory.forEach { rental ->
                    val preu = calcularPreu(rental.startTime, rental.endTime)
                    Text(
                        text = buildString {
                            append("Comanda: ${rental.rentalId}\n")
                            append("- Nom bici: ${rental.vehicle.name}\n")
                            append("- Tipus bici: ${rental.vehicle.vehicleTypeId}\n")
                            append("- Inici lloguer: ${formatDateTimeHuman(rental.startTime)}\n")
                            append("- Final lloguer: ${formatDateTimeHuman(rental.endTime)}\n")
                            append("- Preu: %.2f €".format(preu))
                        },
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))
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
            fontWeight = FontWeight.Bold,
            modifier = Modifier.width(dimensionResource(R.dimen.label_width))
        )
        Text(text = value)
    }
}

@Composable
fun calcularPreu(startTime: String, endTime: String): Double {
    val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
    val start = LocalDateTime.parse(startTime, formatter)
    val end = LocalDateTime.parse(endTime, formatter)

    val zone = ZoneOffset.UTC
    val startInstant = start.toInstant(zone)
    val endInstant = end.toInstant(zone)

    val duration = Duration.between(startInstant, endInstant)
    val minuts = duration.toMinutes().coerceAtLeast(1)

    val preuDesbloqueig = 1.0
    val preuPerMinut = 0.25
    return preuDesbloqueig + (minuts * preuPerMinut)
}

@Composable
fun formatDateTimeHuman(dateTimeStr: String): String {
    val formatterInput = DateTimeFormatter.ISO_LOCAL_DATE_TIME
    val dateTime = LocalDateTime.parse(dateTimeStr, formatterInput)

    val formatterOutput = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm", Locale.getDefault())
    return dateTime.format(formatterOutput)
}