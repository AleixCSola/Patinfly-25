package cat.deim.asm_32.patinfly.data.datasource.model

import cat.deim.asm_32.patinfly.domain.models.*
import com.google.gson.annotations.SerializedName

data class SystemPricingPlanModel(
    @SerializedName("last_updated")
    val lastUpdated: String?,
    val ttl: String,
    val version: String,
    val data: DataModel
) {
    fun toDomain(): SystemPricingPlan = SystemPricingPlan(
        lastUpdated = this.lastUpdated ?: "",
        ttl = this.ttl,
        version = this.version,
        dataPlan = this.data.plans.firstOrNull()?.toDomain() ?: throw IllegalStateException("No plans found")
    )

    companion object {
        fun fromDomain(plan: SystemPricingPlan): SystemPricingPlanModel = SystemPricingPlanModel(
            lastUpdated = plan.lastUpdated,
            ttl = plan.ttl,
            version = plan.version,
            data = DataModel.fromDomain(plan.dataPlan)
        )
    }
}

data class DataModel(
    val plans: List<InformationModel>
) {
    companion object {
        fun fromDomain(info: Information): DataModel = DataModel(
            plans = listOf(InformationModel.fromDomain(info))
        )
    }
}

data class InformationModel(
    @SerializedName("plan_id")
    val planId: String?,
    val name: List<TextTypeModel>,
    val currency: String,
    val price: Double,
    @SerializedName("is_taxable")
    val isTaxable: Boolean,
    val description: List<TextTypeModel>,
    @SerializedName("per_km_pricing")
    val perKmPricing: List<PerKmPricingModel>?,
    @SerializedName("per_min_pricing")
    val perMinPricing: List<PerMinPricingModel>?
) {
    fun toDomain(): Information = Information(
        planId = this.planId ?: "",
        name = this.name.firstOrNull()?.toDomain() ?: throw IllegalStateException("No name found"),
        currency = this.currency,
        price = this.price,
        isTaxable = this.isTaxable,
        description = this.description.firstOrNull()?.toDomain() ?: throw IllegalStateException("No description found"),
        perKmPricing = this.perKmPricing?.firstOrNull()?.toDomain() ?: PerKmPricing(0.0, 0.0, 0.0),
        perMinPricing = this.perMinPricing?.firstOrNull()?.toDomain() ?: PerMinPricing(0.0, 0.0, 0.0)
    )

    companion object {
        fun fromDomain(info: Information): InformationModel = InformationModel(
            planId = info.planId,
            name = listOf(TextTypeModel.fromDomain(info.name)),
            currency = info.currency,
            price = info.price,
            isTaxable = info.isTaxable,
            description = listOf(TextTypeModel.fromDomain(info.description)),
            perKmPricing = listOf(PerKmPricingModel.fromDomain(info.perKmPricing)),
            perMinPricing = listOf(PerMinPricingModel.fromDomain(info.perMinPricing))
        )
    }
}

data class TextTypeModel(
    val text: String,
    val language: String
) {
    fun toDomain(): TextType = TextType(
        text = this.text,
        language = this.language
    )

    companion object {
        fun fromDomain(textType: TextType): TextTypeModel = TextTypeModel(
            text = textType.text,
            language = textType.language
        )
    }
}

data class PerKmPricingModel(
    val start: Double,
    val rate: Double,
    val interval: Double
) {
    fun toDomain(): PerKmPricing = PerKmPricing(
        start = this.start,
        rate = this.rate,
        interval = this.interval
    )

    companion object {
        fun fromDomain(pricing: PerKmPricing): PerKmPricingModel = PerKmPricingModel(
            start = pricing.start,
            rate = pricing.rate,
            interval = pricing.interval
        )
    }
}

data class PerMinPricingModel(
    val start: Double,
    val rate: Double,
    val interval: Double
) {
    fun toDomain(): PerMinPricing = PerMinPricing(
        start = this.start,
        rate = this.rate,
        interval = this.interval
    )

    companion object {
        fun fromDomain(pricing: PerMinPricing): PerMinPricingModel = PerMinPricingModel(
            start = pricing.start,
            rate = pricing.rate,
            interval = pricing.interval
        )
    }
}