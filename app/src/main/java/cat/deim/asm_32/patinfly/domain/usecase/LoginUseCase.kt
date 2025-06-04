package cat.deim.asm_32.patinfly.domain.usecase

import android.util.Log
import cat.deim.asm_32.patinfly.data.datasource.remote.APIService
import cat.deim.asm_32.patinfly.domain.models.User
import cat.deim.asm_32.patinfly.domain.models.Credentials
import cat.deim.asm_32.patinfly.domain.repository.IUserRepository
import java.util.Date

class LoginUseCase(
    private var userRepository: IUserRepository,
    private val apiService: APIService
) {
    suspend fun execute(credentials: Credentials): Boolean {
        return try {
            // 1. Intentar obtener usuario localmente
            val user = userRepository.getUserByEmail(credentials.email)
            if (user != null && user.hashedPassword == credentials.password) {
                // Login local válido
                true
            } else {
                // 2. Login remoto por API
                Log.d("LoginUseCase", "Intentando login con email: ${credentials.email}, password: ${credentials.password}")
                val loginResponse = apiService.login(credentials.email, credentials.password)
                if (loginResponse.success) {
                    // Guardar usuario localmente (sin token)
                    val token = loginResponse.token
                    val data = Date()
                    val newUser = User(
                        uuid = token.id.toString(),
                        name = "a",
                        email = token.email,
                        hashedPassword = credentials.password,
                        creationDate = data,
                        lastConnection = data,
                        deviceId = "1"
                    )
                    userRepository.setUser(newUser)
                    true
                } else {
                    false
                }
            }
        } catch (error: Exception) {
            Log.e("LoginUseCase", "Error en login", error)
            false
        }
    }
}