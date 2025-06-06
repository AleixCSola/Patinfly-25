package cat.deim.asm_32.patinfly.domain.models

import java.util.Date

data class Bike(
    val uuid: String,
    val name: String,
    val typeUuid: String,
    val isDisabled: Boolean,
    val isReserved: Boolean,
    val isRented: Boolean,
    val lat: Double,
    val lon: Double,
    val userId: String? = null
    /* lastMaintenanceDate: Date?,
    val batteryLvl: Double =100.0,
    val creationDate: Date,
    val type: BikeType,
    val meters: Int=0*/
)