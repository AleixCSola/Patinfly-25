package cat.deim.asm_32.patinfly.domain.repository

import cat.deim.asm_32.patinfly.domain.models.User

interface IUserRepository {
    suspend fun setUser(user: User): Boolean
    suspend fun getUserByEmail(email: String): User?
    suspend fun getById(uuid: String): User?
    suspend fun updateUser(user: User): Boolean
    suspend fun deleteUser(uuid:String): Boolean
}