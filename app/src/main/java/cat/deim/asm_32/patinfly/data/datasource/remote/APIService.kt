package cat.deim.asm_32.patinfly.data.datasource.remote

import LoginResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST


interface APIService {

    @POST("login")
    suspend fun login(
        @Header("email") email: String,
        @Header("password") password: String,
        @Header("Origin") origin: String = ""
    ): LoginResponse

    @GET("user")
    suspend fun getUser(@Header("Authorization") token: String): UserResponse

    @GET("vehicle")
    suspend fun getVehicles(@Header("Authorization") token: String): BikesResponse
}
