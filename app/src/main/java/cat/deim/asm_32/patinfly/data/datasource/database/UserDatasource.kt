package cat.deim.asm_32.patinfly.data.datasource.database

import androidx.room.*
import cat.deim.asm_32.patinfly.data.datasource.database.model.UserDTO

@Dao
interface UserDatasource {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: UserDTO): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(user: UserDTO): Long

    @Query("SELECT * FROM user LIMIT 1")
    suspend fun getUser(): UserDTO?

    @Query("SELECT * FROM user WHERE uuid = :uuid LIMIT 1")
    suspend fun getById(uuid: String): UserDTO?

    @Update
    suspend fun update(user: UserDTO): Int

    @Query("DELETE FROM user WHERE uuid = :uuid")
    suspend fun deleteUser(uuid: String): Int
}