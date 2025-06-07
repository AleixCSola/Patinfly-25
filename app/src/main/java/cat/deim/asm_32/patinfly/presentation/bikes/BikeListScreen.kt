package cat.deim.asm_32.patinfly.presentation.bikes

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import cat.deim.asm_32.patinfly.domain.models.Bike
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import cat.deim.asm_32.patinfly.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BikeListScreen(
    viewModel: BikeListViewModel,
    onProfileClick: () -> Unit,
    onBackClick: () -> Unit
) {
    val bicis by viewModel.bikes.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.bike_list_title))
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_back),
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onProfileClick) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_profile),
                            contentDescription = stringResource(R.string.profile)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(bicis) { bike ->
                EachBike(bici = bike)
            }
        }
    }
}

@Composable
fun EachBike(bici: Bike) {
    val context = LocalContext.current
    val sharedPrefs = context.getSharedPreferences("session", Context.MODE_PRIVATE)
    val userId = sharedPrefs.getString("uuid", "") ?: ""

    val (statusText, statusColor) = when {
        bici.isDisabled -> "No disponible" to Color.Red
        bici.isRented && bici.userId == userId -> "Alquilada per mi" to Color.Yellow
        bici.isRented && bici.userId != userId -> "Alquilada" to Color.Red
        bici.isReserved && bici.userId == userId -> "Reservada per mi" to Color(0xFF448AFF)
        bici.isReserved && bici.userId != userId -> "Reservada" to Color.Red
        else -> "Disponible" to Color.Green
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = dimensionResource(R.dimen.padding_small)),
        elevation = CardDefaults.cardElevation(dimensionResource(R.dimen.elevation_medium)),
        shape = RoundedCornerShape(dimensionResource(R.dimen.corner_radius_medium))
    ) {
        Column(modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))) {
            Image(
                painter = painterResource(id = R.drawable.bike_card),
                contentDescription = stringResource(R.string.bike_image_desc),
                modifier = Modifier
                    .height(dimensionResource(R.dimen.bike_image_height))
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(dimensionResource(R.dimen.corner_radius_large)))
            )

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_medium)))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = bici.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = statusText,
                    color = statusColor
                )
            }

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_medium)))
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_medium)))

            Button(
                onClick = {
                    val intent = Intent(context, BikeDetailActivity::class.java).apply {
                        putExtra("bike_uuid", bici.uuid)
                    }
                    context.startActivity(intent)
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(dimensionResource(R.dimen.corner_radius_small)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(stringResource(R.string.view_details), color = Color.White)
            }
        }
    }
}
