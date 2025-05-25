package cat.deim.asm_32.patinfly.data.datasource.database

import androidx.room.*
import cat.deim.asm_32.patinfly.data.datasource.database.model.BikeDTO

@Dao
interface BikeDatasource {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(bike: BikeDTO): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(bikes: List<BikeDTO>): List<Long>

    @Query("SELECT * FROM bike")
    suspend fun getAll(): List<BikeDTO>

    @Query("SELECT * FROM bike WHERE uuid = :uuid")
    suspend fun getById(uuid: String): BikeDTO?

    @Update
    suspend fun update(bike: BikeDTO): Int

    @Query("DELETE FROM bike WHERE uuid = :uuid")
    suspend fun delete(uuid: String): Int
}