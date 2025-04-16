package cat.deim.asm_32.patinfly.presentation.bikes

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cat.deim.asm_32.patinfly.domain.models.Bike
import cat.deim.asm_32.patinfly.domain.models.BikeType
import java.util.*

@Composable
fun BikeListScreen(viewModel: BikeViewModel = BikeViewModel()) {
    val bikes by viewModel.bikes.collectAsState()

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(bikes) { bike ->
            BikeRow(bike = bike)
        }
    }
}

@Composable
fun BikeRow(bike: Bike) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = bike.name, style = MaterialTheme.typography.titleLarge)
            Text(text = "Battery: ${bike.batteryLvl.toInt()}%")
            Text(text = "Meters: ${bike.meters} m")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBikeListScreen() {
    val fakeBikes = List(3) {
        Bike(
            uuid = "bike_$it",
            name = "Bike #$it",
            type = BikeType("1", "Urban", "Regular"),
            creationDate = Date(),
            lastMaintenanceDate = Date(),
            isActive = true,
            batteryLvl = 85.0,
            meters = 500
        )
    }
    LazyColumn {
        items(fakeBikes) {
            BikeRow(it)
        }
    }
}
