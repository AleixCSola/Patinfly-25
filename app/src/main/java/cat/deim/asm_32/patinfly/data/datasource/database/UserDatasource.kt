package cat.deim.asm_32.patinfly.data.datasource.database

import androidx.room.*
import cat.deim.asm_32.patinfly.data.datasource.database.model.UserDTO

@Dao
interface UserDatasource {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userDTO: UserDTO): Long

    @Query("SELECT * FROM user WHERE uuid = :uuid")
    suspend fun getUserByUUID(uuid: String): UserDTO?

    @Query("SELECT * FROM user WHERE email = :email")
    suspend fun getUserByMail(email: String): UserDTO?

    @Query("SELECT * FROM user")
    suspend fun getAll(): List<UserDTO>

    @Update
    suspend fun update(userDTO: UserDTO): Int

    @Query("DELETE FROM user WHERE uuid = :uuid")
    suspend fun delete(uuid: String): Int
}