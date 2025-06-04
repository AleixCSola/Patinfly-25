package cat.deim.asm_32.patinfly.data.datasource.remote

import LoginResponse
import retrofit2.http.Header
import retrofit2.http.POST


interface APIService {

    @POST("login")
    suspend fun login(
        @Header("email") email: String,
        @Header("password") password: String
    ): LoginResponse
    }
