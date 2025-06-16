package cat.deim.asm_32.patinfly.data.datasource.remote

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

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
    @POST("vehicle/reserve/{uuid}")
    suspend fun doReserve(
        @Header("Authorization") token: String,
        @Path("uuid") uuid: String,
        @Header("Origin") origin: String = ""
    ): ReserveResponse
    @POST("vehicle/release/{uuid}")
    suspend fun doRelease(
        @Header("Authorization") token: String,
        @Path("uuid") uuid: String,
        @Header("Origin") origin: String = ""
    ): ReleaseResponse
    @POST("rent/start/{uuid}")
    suspend fun doStart(
        @Header("Authorization") token: String,
        @Path("uuid") uuid: String,
        @Header("Origin") origin: String = ""
    ): StartResponse
    @POST("rent/stop/{uuid}")
    suspend fun doStop(
        @Header("Authorization") token: String,
        @Path("uuid") uuid: String,
        @Header("Origin") origin: String = ""
    ): StopResponse

    @GET("rent")
    suspend fun getRentalHistory(@Header("Authorization") token: String): List<RentalResponse?>
}
