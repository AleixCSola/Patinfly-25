package cat.deim.asm_32.patinfly.domain.usecase

import cat.deim.asm_32.patinfly.domain.models.Bike
import cat.deim.asm_32.patinfly.domain.models.SystemPricingPlan
import cat.deim.asm_32.patinfly.domain.repository.IBikeRepository
import cat.deim.asm_32.patinfly.domain.repository.ISystemPricingPlanRepository

class BikeRentDetailUseCase(private val bikeRepository: IBikeRepository,
                            private val pricingPlanRepository: ISystemPricingPlanRepository
) {

    suspend fun toggleRent(bikeUuid: String, userId: String): Boolean {
        val bike = bikeRepository.getById(bikeUuid) ?: return false

        val isUserCurrentlyRenting = bike.isRented && bike.userId == userId

        val updatedBike = bike.copy(
            isRented = !isUserCurrentlyRenting,
            userId = if (isUserCurrentlyRenting) null else userId,
            isReserved = false
        )

        return bikeRepository.update(updatedBike)
    }

    suspend fun isBikeRentedByUser(bikeUuid: String, userId: String): Boolean {
        val bike = bikeRepository.getById(bikeUuid) ?: return false
        return bike.isRented && bike.userId == userId
    }

    suspend fun getBikeById(bikeUuid: String): Bike? {
        return bikeRepository.getById(bikeUuid)
    }

    suspend fun getPricingPlan(): SystemPricingPlan? {
        return pricingPlanRepository.getById("plan2025")
    }

}
