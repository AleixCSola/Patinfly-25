import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Token(
    @SerialName(value = "id")
    val id: String,
    @SerialName(value = "email")
    val email: String,
    @SerialName(value = "access")
    val access: String,
    @SerialName(value = "expires")
    val expires: String,
    @SerialName(value = "refresh")
    val refresh: String,
    @SerialName(value = "expires_refresh")
    val expiresRefresh: String
)

@Serializable
data class LoginResponse(
    @SerialName(value = "boolean")
    val success: Boolean,
    val token: Token,
    @SerialName(value = "version")
    val version: String
)