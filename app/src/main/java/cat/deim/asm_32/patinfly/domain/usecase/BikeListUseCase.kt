package cat.deim.asm_32.patinfly.domain.usecase

import cat.deim.asm_32.patinfly.domain.models.Bike
import cat.deim.asm_32.patinfly.domain.repository.IBikeRepository

class BikeListUseCase(
    private val repository: IBikeRepository,
    private val token: String
) {
    suspend fun execute(): List<Bike> {
        return repository.getAll(token).toList()
    }
}