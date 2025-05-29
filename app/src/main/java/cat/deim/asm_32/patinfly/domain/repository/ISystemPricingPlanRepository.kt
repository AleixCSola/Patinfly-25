package cat.deim.asm_32.patinfly.domain.repository

import cat.deim.asm_32.patinfly.domain.models.SystemPricingPlan

interface ISystemPricingPlanRepository {
    suspend fun insert(systemPricingPlan: SystemPricingPlan): Boolean
    suspend fun getById(planId: String): SystemPricingPlan?
    suspend fun update(systemPricingPlan: SystemPricingPlan): Boolean
    suspend fun delete(planId: String): Boolean
}