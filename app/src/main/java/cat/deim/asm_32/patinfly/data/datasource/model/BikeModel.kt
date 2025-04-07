package cat.deim.asm_32.patinfly.data.datasource.model

import cat.deim.asm_32.patinfly.domain.models.Bike
import java.util.Date

data class BikeModel(
    val uuid: String,
    val name: String,
    val type: BikeTypeModel,
    val creationDate: Date,
    val lastMaintenanceDate: String,
    val isActive: Boolean,
    val batteryLvl: Double,
    val meters: Int
){
    fun toDomain(): Bike =Bike(
        uuid=this.uuid,
        name=this.name,
        type=this.type.toDomain(),
        creationDate=this.creationDate,
        lastMaintenanceDate=this.lastMaintenanceDate,
        isActive=this.isActive,
        batteryLvl=this.batteryLvl,
        meters=this.meters
    )

    companion object{
        fun fromDomain(bike: Bike): BikeModel=BikeModel(
            uuid=bike.uuid,
            name=bike.name,
            type=BikeTypeModel.fromDomain(bike.type),
            creationDate=bike.creationDate,
            lastMaintenanceDate=bike.lastMaintenanceDate,
            isActive=bike.isActive,
            batteryLvl=bike.batteryLvl,
            meters=bike.meters
        )
    }
}
