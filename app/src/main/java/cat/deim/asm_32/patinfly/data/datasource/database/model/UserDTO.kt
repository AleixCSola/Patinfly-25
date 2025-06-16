package cat.deim.asm_32.patinfly.data.datasource.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import cat.deim.asm_32.patinfly.domain.models.User
import java.util.Date

@Entity(tableName = "user")
data class UserDTO(
    @PrimaryKey val uuid: String,
    val name: String,
    val email: String,
    val hashedPassword: String,
    val creationDate: Date,
    val lastConnection: Date,
    val deviceId: String
) {
    companion object {
        fun fromDomain(user: User): UserDTO = UserDTO(
            uuid = user.uuid,
            name = user.name,
            email = user.email,
            hashedPassword = user.hashedPassword,
            creationDate = user.creationDate,
            lastConnection = user.lastConnection,
            deviceId = user.deviceId
        )
    }
    fun toDomain(): User = User(
        uuid = uuid,
        name = name,
        email = email,
        hashedPassword = hashedPassword,
        creationDate = creationDate,
        lastConnection = lastConnection,
        deviceId = deviceId
    )
}