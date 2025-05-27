package cat.deim.asm_32.patinfly.data.datasource.database

import androidx.room.*
import cat.deim.asm_32.patinfly.data.datasource.database.model.BikeDTO

@Dao
interface BikeDatasource {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(bike: BikeDTO): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(bikes: List<BikeDTO>): Boolean

    @Query("SELECT * FROM bike")
    fun getAll(): List<BikeDTO>

    @Query("SELECT * FROM bike WHERE uuid = :uuid")
    fun getById(uuid: String): BikeDTO?

    @Update
    fun update(bike: BikeDTO): Boolean

    @Query("DELETE FROM bike WHERE uuid = :uuid")
    fun delete(uuid: String): Boolean
}