package cat.deim.asm_32.patinfly.domain.repository

import cat.deim.asm_32.patinfly.domain.models.Bike
import cat.deim.asm_32.patinfly.domain.models.SystemPricingPlan

interface ISystemPricingPlanRepository {
    fun insert(systemPricingPlan: SystemPricingPlan): Boolean
    fun getAll(): Collection<SystemPricingPlan>
    fun getById(planId: String): SystemPricingPlan?
    fun update(systemPricingPlan: SystemPricingPlan): Boolean
    fun delete(planId: String): Boolean
}