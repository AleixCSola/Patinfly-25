package cat.deim.asm_32.patinfly.data.datasource.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date


@Entity(tableName = "system_pricing_plan")
data class SystemPricingPlanDTO(
    @PrimaryKey val planId: String,
    val lastUpdated: String,
    val ttl: String,
    val version: Double,

    val nameText: String,
    val nameLanguage: String,
    val currency: String,
    val price: Double,
    val isTaxable: Boolean,
    val descriptionText: String,
    val descriptionLanguage: String,

    val perKmStart: Double,
    val perKmRate: Double,
    val perKmInterval: Double,

    val perMinStart: Double,
    val perMinRate: Double,
    val perMinInterval: Double
)