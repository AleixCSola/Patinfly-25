package cat.deim.asm_32.patinfly.data.repository

import android.util.Log
import cat.deim.asm_32.patinfly.data.datasource.ISystemPricingPlanDataSource
import cat.deim.asm_32.patinfly.data.datasource.model.SystemPricingPlanModel
import cat.deim.asm_32.patinfly.domain.models.SystemPricingPlan
import cat.deim.asm_32.patinfly.domain.repository.ISystemPricingPlanRepository
import cat.deim.asm_32.patinfly.data.datasource.database.SystemPricingPlanDatasource
import cat.deim.asm_32.patinfly.data.datasource.database.model.SystemPricingPlanDTO

class SystemPricingPlanRepository(
    private val dao: SystemPricingPlanDatasource,
    private val localDataSource: ISystemPricingPlanDataSource
) : ISystemPricingPlanRepository {

    override suspend fun insert(plan: SystemPricingPlan): Boolean {
        return dao.insert(SystemPricingPlanDTO.fromDomain(plan)) > 0
    }

    override suspend fun getById(planId: String): SystemPricingPlan? {
        val planInDb = dao.getById(planId)
        if (planInDb != null) return planInDb.toDomain()
        else {
            val localPlan = localDataSource.getById(planId)
            Log.d("SystemPricingPlanRepository", "localPlan: $localPlan")
            return localPlan?.let { planModel ->
                dao.insert(SystemPricingPlanDTO.fromDomain(planModel.toDomain()))
                planModel.toDomain()
            }
        }
    }

    override suspend fun update(plan: SystemPricingPlan): Boolean {
        return dao.update(SystemPricingPlanDTO.fromDomain(plan)) > 0
    }

    override suspend fun delete(planId: String): Boolean {
        return dao.delete(planId) > 0
    }
}