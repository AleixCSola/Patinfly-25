package cat.deim.asm_32.patinfly.data.repository

import cat.deim.asm_32.patinfly.data.datasource.IUserDataSource
import cat.deim.asm_32.patinfly.data.datasource.model.UserModel
import cat.deim.asm_32.patinfly.domain.models.User
import cat.deim.asm_32.patinfly.domain.repository.IUserRepository
import cat.deim.asm_32.patinfly.data.datasource.database.UserDatasource
import cat.deim.asm_32.patinfly.data.datasource.database.model.UserDTO


class UserRepository(
    private val userDao: UserDatasource, private val localDataSource: IUserDataSource
) : IUserRepository {

    override fun setUser(user: User): Boolean {
        val dto = UserDTO.fromDomain(user)
        return userDao.insert(dto) > 0
    }

    override fun getUser(): User? {
        val userInDb = userDao.getAll().firstOrNull()
        return if (userInDb != null) {
            userInDb.toDomain()
        } else {
            val localUser = localDataSource.getUser()
            localUser?.let {
                val inserted = userDao.insert(UserDTO.fromDomain(it.toDomain()))
                if (inserted != -1L) it.toDomain() else null
            }
        }
    }

    override fun getById(uuid: String): User? {
        val userInDb = userDao.getUserByUUID(uuid)
        return if (userInDb != null) {
            userInDb.toDomain()
        } else {
            val localUser = localDataSource.getById(uuid)
            localUser?.let {
                val inserted = userDao.insert(UserDTO.fromDomain(it.toDomain()))
                if (inserted != -1L) it.toDomain() else null
            }
        }
    }

    override fun updateUser(user: User): Boolean {
        val dto = UserDTO.fromDomain(user)
        return userDao.update(dto) > 0
    }

    override fun deleteUser(uuid: String): Boolean {
        return userDao.delete(uuid) > 0
    }
}

