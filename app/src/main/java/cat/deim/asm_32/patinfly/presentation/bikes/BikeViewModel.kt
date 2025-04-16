package cat.deim.asm_32.patinfly.presentation.bikes

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import cat.deim.asm_32.patinfly.domain.models.Bike
import cat.deim.asm_32.patinfly.domain.models.BikeType
import java.time.LocalDateTime
import java.util.*

class BikeViewModel : ViewModel() {
    private val _bikes = MutableStateFlow<List<Bike>>(emptyList())
    val bikes: StateFlow<List<Bike>> = _bikes

    init {
        loadBikes()
    }

    private fun loadBikes() {
        _bikes.value = List(20) { index ->
            Bike(
                uuid = "bike_$index",
                name = "Bike #$index",
                type = BikeType(uuid = "type1", name = "Urban", type = "Regular"),
                creationDate = Date(),
                lastMaintenanceDate = Date(),
                isActive = true,
                batteryLvl = (50..100).random().toDouble(),
                meters = (100..1000).random()
            )
        }
    }
}
