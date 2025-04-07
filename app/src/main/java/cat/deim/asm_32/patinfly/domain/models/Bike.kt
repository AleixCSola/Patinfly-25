package cat.deim.asm_32.patinfly.domain.models

import java.util.Date
data class Bike(
    val uuid: String,
    val name: String,
    val type: BikeType,
    val creationDate: Date,
    val lastMaintenanceDate: String,
    val isActive: Boolean,
    val batteryLvl: Double,
    val meters: Int
)