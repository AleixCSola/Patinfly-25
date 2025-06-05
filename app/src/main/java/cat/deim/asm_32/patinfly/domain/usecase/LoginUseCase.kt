package cat.deim.asm_32.patinfly.domain.usecase

import android.util.Log
import cat.deim.asm_32.patinfly.data.datasource.remote.APIService
import cat.deim.asm_32.patinfly.domain.models.User
import cat.deim.asm_32.patinfly.domain.models.Credentials
import cat.deim.asm_32.patinfly.domain.repository.IUserRepository
import android.content.Context
import java.util.Date

class LoginUseCase(
    private var userRepository: IUserRepository,
    private val apiService: APIService,
) {
    suspend fun execute(credentials: Credentials, context: Context): Boolean {
        return try {
            Log.d("LoginUseCase", "Intentando login con email: ${credentials.email}, password: ${credentials.password}")
            val loginResponse = apiService.login(credentials.email, credentials.password)

            if (loginResponse.success) {
                val token = loginResponse.token
                val sharedPrefs = context.getSharedPreferences("session", Context.MODE_PRIVATE)
                sharedPrefs.edit().putString("token", token.access).apply()
                sharedPrefs.edit().putString("email", credentials.email).apply()

                val userData = apiService.getUser("Bearer ${token.access}")
                val now = Date()
                val domainUser = User(
                    uuid = userData.uuid,
                    name = userData.fullname,
                    email = userData.email,
                    hashedPassword = credentials.password,
                    creationDate = now,
                    lastConnection = now,
                    deviceId = "1"
                )

                userRepository.setUser(domainUser)
                Log.d("LoginUseCase", "Usuario guardado correctamente: $domainUser")
                true
            } else {
                false
            }
        } catch (error: Exception) {
            Log.e("LoginUseCase", "Error en login", error)
            false
        }
    }
}