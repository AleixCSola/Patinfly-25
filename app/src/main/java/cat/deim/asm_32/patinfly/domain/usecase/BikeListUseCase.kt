package cat.deim.asm_32.patinfly.domain.usecase

import cat.deim.asm_32.patinfly.domain.models.Bike
import cat.deim.asm_32.patinfly.domain.repository.IBikeRepository

class BikeListUseCase(
    private val repository: IBikeRepository
) {
    suspend fun execute(): List<Bike> {
        return repository.getAll().toList()
    }
}