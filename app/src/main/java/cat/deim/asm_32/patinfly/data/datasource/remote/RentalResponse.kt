package cat.deim.asm_32.patinfly.data.datasource.remote

import com.google.gson.annotations.SerializedName

data class RentalResponse(
    @SerializedName("uuid") val rentalId: String,
    @SerializedName("vehicle") val vehicle: VehicleRent,
    @SerializedName("user") val user: User,
    @SerializedName("start_time") val startTime: String,
    @SerializedName("end_time") val endTime: String
)

data class VehicleRent(
    @SerializedName("vehicle_id") val vehicleId: String,
    @SerializedName("vehicle_type_id") val vehicleTypeId: String,
    @SerializedName("name") val name: String,
    @SerializedName("lat") val lat: Double,
    @SerializedName("lon") val lon: Double
)

data class User(
    @SerializedName("uuid") val userId: String,
    @SerializedName("fullname") val fullname: String,
    @SerializedName("email") val email: String
)
