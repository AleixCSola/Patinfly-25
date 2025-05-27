package cat.deim.asm_32.patinfly.data.datasource.database

import androidx.room.*
import cat.deim.asm_32.patinfly.data.datasource.database.model.UserDTO

@Dao
interface UserDatasource {

    @Insert
    fun insert(user: UserDTO): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdate(user: UserDTO): Boolean

    @Query("SELECT * FROM user")
    fun getUser(): UserDTO?

    @Query("SELECT * FROM user WHERE uuid = :uuid")
    fun getById(uuid: String): UserDTO?

    @Update
    fun update(user: UserDTO): Boolean

    @Query("DELETE FROM user WHERE uuid = :uuid")
    fun deleteUser(uuid: String): Boolean
}