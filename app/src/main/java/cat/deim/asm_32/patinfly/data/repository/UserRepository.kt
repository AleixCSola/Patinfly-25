package cat.deim.asm_32.patinfly.data.repository

import cat.deim.asm_32.patinfly.data.datasource.IUserDataSource
import cat.deim.asm_32.patinfly.data.datasource.model.UserModel
import cat.deim.asm_32.patinfly.domain.models.User
import cat.deim.asm_32.patinfly.domain.repository.IUserRepository
import cat.deim.asm_32.patinfly.data.datasource.database.UserDatasource
import cat.deim.asm_32.patinfly.data.datasource.database.model.UserDTO


class UserRepository(
    private val userDao: UserDatasource
) : IUserRepository {

    override suspend fun setUser(user: User): Boolean {
        val dto = UserDTO.fromDomain(user)
        return userDao.insert(dto) > 0
    }

    override suspend fun getUserByEmail(email: String): User? {
        val userInDb = userDao.getUserByMail(email)
        return if (userInDb != null) {
            userInDb.toDomain()
        } else {
             null

        }
    }

    override suspend fun updateUser(user: User): Boolean {
        val dto = UserDTO.fromDomain(user)
        return userDao.update(dto) > 0
    }

    override suspend fun deleteUser(uuid: String): Boolean {
        return userDao.delete(uuid) > 0
    }
}

