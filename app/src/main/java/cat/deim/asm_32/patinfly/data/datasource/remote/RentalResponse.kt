package cat.deim.asm_32.patinfly.data.datasource.remote

import com.google.gson.annotations.SerializedName

data class RentalResponse(
    @SerializedName("bike_id") val bikeId: String,
    @SerializedName("user_id") val userId: String,
    @SerializedName("start_time") val startTime: String,
    @SerializedName("end_time") val endTime: String,
    @SerializedName("vehicle_type_id") val vehicleTypeId: String
)