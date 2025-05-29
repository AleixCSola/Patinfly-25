package cat.deim.asm_32.patinfly.data.datasource.database

import androidx.room.*
import cat.deim.asm_32.patinfly.data.datasource.database.model.SystemPricingPlanDTO

@Dao
interface SystemPricingPlanDatasource {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(plan: SystemPricingPlanDTO): Long

    @Query("SELECT * FROM system_pricing_plan WHERE planId = :planId")
    suspend fun getById(planId: String): SystemPricingPlanDTO?

    @Update
    suspend fun update(plan: SystemPricingPlanDTO): Int

    @Query("DELETE FROM system_pricing_plan WHERE planId = :planId")
    suspend fun delete(planId: String): Int
}