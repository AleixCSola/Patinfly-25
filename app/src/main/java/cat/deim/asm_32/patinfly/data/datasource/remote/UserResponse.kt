package cat.deim.asm_32.patinfly.data.datasource.remote

import cat.deim.asm_32.patinfly.domain.models.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
data class UserResponse(
    @SerialName("uuid") val uuid: String,
    @SerialName("firstname") val firstname: String,
    @SerialName("lastname") val lastname: String,
    @SerialName("fullname") val fullname: String,
    @SerialName("email") val email: String,
    @SerialName("subject") val subject: String
) {
    fun UserResponse.toDomainUser(
        hashedPassword: String,
        creationDate: Date = Date(),
        lastConnection: Date = Date(),
        deviceId: String = "1"
    ): User {
        return User(
            uuid = uuid,
            name = fullname,
            email = email,
            hashedPassword = hashedPassword,
            creationDate = creationDate,
            lastConnection = lastConnection,
            deviceId = deviceId
        )
    }
}