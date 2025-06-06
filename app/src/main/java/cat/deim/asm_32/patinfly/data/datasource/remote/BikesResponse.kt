package cat.deim.asm_32.patinfly.data.datasource.remote;

import cat.deim.asm_32.patinfly.domain.models.Bike
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Vehicle(
        @SerialName(value = "name")
        val name: String,

        @SerialName(value = "is_disabled")
        val isDisabled: Boolean,

        @SerialName(value = "is_reserved")
        val isReserved: Boolean,

        @SerialName(value = "is_rented")
        val isRented: Boolean,

        @SerialName(value = "last_reported")
        val lastReported: String,

        @SerialName(value = "lat")
        val lat: Double,

        @SerialName(value = "lon")
        val lon: Double,

        @SerialName(value = "rental_uris")
        val rentalUris: RentalUris,

        @SerialName(value = "vehicle_id")
        val vehicleId: String,

        @SerialName(value = "vehicle_type_id")
        val vehicleTypeId: String,

        @SerialName(value = "group_course")
        val groupCourse: String
)

@Serializable
data class RentalUris(
        @SerialName(value = "android")
        val android: String,

        @SerialName(value = "ios")
        val ios: String
)

@Serializable
data class BikesResponse(
        @SerialName(value = "vehicles")
        val vehicles: List<Vehicle>,
        @SerialName(value = "version")
        val version: String
) {
        fun toDomain(): List<Bike> = vehicles.map {
                Bike(
                        name = it.name,
                        isDisabled = it.isDisabled,
                        isReserved = it.isReserved,
                        isRented = it.isRented,
                        lat = it.lat,
                        lon = it.lon,
                        uuid = it.vehicleId,
                        typeUuid = it.vehicleTypeId
                )
        }
}

