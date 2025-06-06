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
    val isDisabled: Boolean,
    val isReserved: Boolean,
    val isRented: Boolean,
    val lat: Double,
    val lon: Double,
    val userId: String? = null
    //val creationDate: Date,
    //val lastMaintenanceDate: Date?,
    //val batteryLvl: Double,
    //val meters: Int
    //val typeName: String,
    //val typeType: String,
) {
    companion object {
        fun fromDomain(bike: Bike): BikeDTO = BikeDTO(
            uuid                = bike.uuid,
            name                = bike.name,
            typeUuid            = bike.typeUuid,
            isDisabled          = bike.isDisabled,
            isReserved          = bike.isReserved,
            isRented            = bike.isRented,
            lat                 = bike.lat,
            lon                 = bike.lon,
            userId              = bike.userId
            /*typeName            = bike.type.name,
            typeType            = bike.type.type,
            creationDate        = bike.creationDate,
            lastMaintenanceDate = bike.lastMaintenanceDate,
            isActive            = bike.isActive,
            batteryLvl          = bike.batteryLvl,
            meters              = bike.meters*/
        )
    }

    fun toDomain(): Bike = Bike(
        uuid                = uuid,
        name                = name,
        typeUuid            = typeUuid,
        isDisabled          = isDisabled,
        isReserved          = isReserved,
        isRented            = isRented,
        lat                 = lat,
        lon                 = lon,
        userId              = userId

        /*creationDate        = creationDate,
        lastMaintenanceDate = lastMaintenanceDate,
        isActive            = isActive,
        batteryLvl          = batteryLvl,
        meters              = meters*/
    )
}