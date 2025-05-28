package cat.deim.asm_32.patinfly.data.datasource.database

import androidx.room.*
import cat.deim.asm_32.patinfly.data.datasource.database.model.SystemPricingPlanDTO

@Dao
interface SystemPricingPlanDatasource {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(plan: SystemPricingPlanDTO): Long

    @Query("SELECT * FROM system_pricing_plan WHERE planId = :planId")
    fun getById(planId: String): SystemPricingPlanDTO?

    @Update
    fun update(plan: SystemPricingPlanDTO): Int

    @Query("DELETE FROM system_pricing_plan WHERE planId = :planId")
    fun delete(planId: String): Int
}