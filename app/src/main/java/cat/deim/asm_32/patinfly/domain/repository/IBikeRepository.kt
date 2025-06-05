package cat.deim.asm_32.patinfly.domain.repository

import cat.deim.asm_32.patinfly.domain.models.Bike

interface IBikeRepository {
    suspend fun insert(bike: Bike): Boolean
    //suspend fun loadLocalData()
    suspend fun getAll(): Collection<Bike>
    suspend fun getById(uuid: String): Bike?
    suspend fun update(bike: Bike): Boolean
    suspend fun delete(uuid: String): Boolean
}