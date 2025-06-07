package cat.deim.asm_32.patinfly.data.datasource.remote;

import cat.deim.asm_32.patinfly.domain.models.Bike
import com.google.gson.annotations.SerializedName

data class BikesResponse(
        @SerializedName("vehicles") val vehicles: List<Vehicle>,
        @SerializedName("version") val version: String
)

data class Vehicle(
        @SerializedName("name") val name: String,
        @SerializedName("is_disabled") val isDisabled: Boolean,
        @SerializedName("is_reserved") val isReserved: Boolean,
        @SerializedName("is_rented") val isRented: Boolean,
        @SerializedName("last_reported") val lastReported: String,
        @SerializedName("lat") val latitude: Double,
        @SerializedName("lon") val longitude: Double,
        @SerializedName("rental_uris") val rentalUris: RentalUris,
        @SerializedName("vehicle_id") val vehicleId: String,
        @SerializedName("vehicle_type_id") val vehicleTypeId: String,
        @SerializedName("group_course") val groupCourse: String
)

data class RentalUris(
        @SerializedName("android") val android: String,
        @SerializedName("ios") val ios: String
)

fun List<Vehicle>.toDomain(): List<Bike> {
        return this.map { vehicle ->
                Bike(
                        uuid = vehicle.vehicleId,
                        name = vehicle.name,
                        typeUuid = vehicle.vehicleTypeId,
                        isDisabled = vehicle.isDisabled,
                        isReserved = vehicle.isReserved,
                        isRented = vehicle.isRented,
                        lat = vehicle.latitude,
                        lon = vehicle.longitude,
                        userId = null
                )
        }
}