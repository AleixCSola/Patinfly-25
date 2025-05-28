package cat.deim.asm_32.patinfly.data.datasource.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import cat.deim.asm_32.patinfly.domain.models.*

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
) {
    fun toDomain(): SystemPricingPlan = SystemPricingPlan(
        lastUpdated = lastUpdated,
        ttl = ttl,
        version = version,
        dataPlan = Information(
            planId = planId,
            name = TextType(nameText, nameLanguage),
            currency = currency,
            price = price,
            isTaxable = isTaxable,
            description = TextType(descriptionText, descriptionLanguage),
            perKmPricing = PerKmPricing(perKmStart, perKmRate, perKmInterval),
            perMinPricing = PerMinPricing(perMinStart, perMinRate, perMinInterval)
        )
    )

    companion object {
        fun fromDomain(plan: SystemPricingPlan): SystemPricingPlanDTO {
            val info = plan.dataPlan
            return SystemPricingPlanDTO(
                planId = info.planId,
                lastUpdated = plan.lastUpdated,
                ttl = plan.ttl,
                version = plan.version,

                nameText = info.name.text,
                nameLanguage = info.name.language,
                currency = info.currency,
                price = info.price,
                isTaxable = info.isTaxable,
                descriptionText = info.description.text,
                descriptionLanguage = info.description.language,

                perKmStart = info.perKmPricing.start,
                perKmRate = info.perKmPricing.rate,
                perKmInterval = info.perKmPricing.interval,

                perMinStart = info.perMinPricing.start,
                perMinRate = info.perMinPricing.rate,
                perMinInterval = info.perMinPricing.interval
            )
        }
    }
}