package cat.deim.asm_32.patinfly.domain.models

import java.util.UUID

data class Bike(
    val uuid: String,
    val name: String,
    val type: BikeType,
    val creationDate: String,
    val lastMaintenanceDate: String,
    val isAvailable: Boolean,
    val batteryLvl: Double,
    val distance: Double
)

