package cat.deim.asm_32.patinfly.data.datasource.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import cat.deim.asm_32.patinfly.domain.models.Bike
import cat.deim.asm_32.patinfly.domain.models.BikeType
import androidx.room.TypeConverters
import java.util.Date

@Entity(tableName = "bike")
data class BikeDTO(
    @PrimaryKey val uuid: String,
    val name: String,
    val typeUuid: String,
    val typeName: String,
    val typeType: String,
    val creationDate: Date,
    val lastMaintenanceDate: Date?,
    val isActive: Boolean,
    val batteryLvl: Double,
    val meters: Int
) {
    companion object {
        fun fromDomain(bike: Bike): BikeDTO = BikeDTO(
            uuid                = bike.uuid,
            name                = bike.name,
            typeUuid            = bike.type.uuid,
            typeName            = bike.type.name,
            typeType            = bike.type.type,
            creationDate        = bike.creationDate,
            lastMaintenanceDate = bike.lastMaintenanceDate,
            isActive            = bike.isActive,
            batteryLvl          = bike.batteryLvl,
            meters              = bike.meters
        )
    }

    fun toDomain(): Bike = Bike(
        uuid                = uuid,
        name                = name,
        type                = BikeType(typeUuid, typeName, typeType),
        creationDate        = creationDate,
        lastMaintenanceDate = lastMaintenanceDate,
        isActive            = isActive,
        batteryLvl          = batteryLvl,
        meters              = meters
    )
}