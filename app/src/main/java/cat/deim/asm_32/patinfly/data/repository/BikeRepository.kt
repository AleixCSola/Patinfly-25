package cat.deim.asm_32.patinfly.data.repository

import cat.deim.asm_32.patinfly.data.datasource.IBikeDataSource
import cat.deim.asm_32.patinfly.data.datasource.model.BikeModel
import cat.deim.asm_32.patinfly.domain.models.Bike
import cat.deim.asm_32.patinfly.domain.repository.IBikeRepository
import cat.deim.asm_32.patinfly.data.datasource.database.BikeDatasource
import cat.deim.asm_32.patinfly.data.datasource.database.model.BikeDTO

class BikeRepository(
    private val bikeDao: BikeDatasource, private val bikeLocalDataSource: IBikeDataSource
) : IBikeRepository {

    override fun insert(bike: Bike): Boolean {
        val dto = BikeDTO.fromDomain(bike)
        return bikeDao.insert(dto) > 0
    }

    override fun getAll(): Collection<Bike> {
        val dbBikes = bikeDao.getAll()
        return if (dbBikes.isNotEmpty()) {
            dbBikes.map { it.toDomain() }
        } else {
            val localBikes = bikeLocalDataSource.getAll()
            if (localBikes.isNotEmpty()) {
                bikeDao.insertAll(localBikes.map { BikeDTO.fromDomain(it.toDomain()) })
                localBikes.map { it.toDomain() }
            } else {
                emptyList()
            }
        }
    }

    override fun update(bike: Bike): Boolean {
        val dto = BikeDTO.fromDomain(bike)
        return bikeDao.update(dto) > 0
    }

    override fun getById(uuid: String): Bike? {
        val dbBike = bikeDao.getById(uuid)
        return if (dbBike != null) {
            dbBike.toDomain()
        } else {
            val localBike = bikeLocalDataSource.getById(uuid)
            localBike?.let {
                bikeDao.insert(BikeDTO.fromDomain(it.toDomain()))
                it.toDomain()
            }
        }
    }

    override fun delete(uuid: String): Boolean {
        return bikeDao.delete(uuid) > 0
    }
}