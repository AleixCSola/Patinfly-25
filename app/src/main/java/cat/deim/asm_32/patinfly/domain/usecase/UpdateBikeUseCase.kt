package cat.deim.asm_32.patinfly.domain.usecase

import cat.deim.asm_32.patinfly.domain.models.Bike
import cat.deim.asm_32.patinfly.domain.repository.IBikeRepository

class UpdateBikeUseCase(private val bikeRepository: IBikeRepository) {
    suspend operator fun invoke(bike: Bike) {
        bikeRepository.update(bike)
    }
}