import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Token(
    val id: Int,
    val email: String,
    val access: String,
    val expires: String,
    val refresh: String,
    @SerialName("expires_refresh") val expiresRefresh: String
)

@Serializable
data class LoginResponse(
    val success: Boolean,
    val token: Token,
    val version: String
)