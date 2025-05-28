package cat.deim.asm_32.patinfly.data.datasource.database

import androidx.room.*
import cat.deim.asm_32.patinfly.data.datasource.database.model.UserDTO

@Dao
interface UserDatasource {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(userDTO: UserDTO): Long

    @Query("SELECT * FROM user WHERE uuid = :uuid")
    fun getUserByUUID(uuid: String): UserDTO?

    @Query("SELECT * FROM user WHERE email = :email")
    fun getUserByMail(email: String): UserDTO?

    @Query("SELECT * FROM user")
    fun getAll(): List<UserDTO>

    @Update
    fun update(userDTO: UserDTO): Int

    @Query("DELETE FROM user WHERE uuid = :uuid")
    fun delete(uuid: String): Int
}