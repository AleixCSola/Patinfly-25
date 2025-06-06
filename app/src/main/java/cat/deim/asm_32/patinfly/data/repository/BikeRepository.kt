package cat.deim.asm_32.patinfly.data.repository

import android.util.Log
import cat.deim.asm_32.patinfly.domain.models.Bike
import cat.deim.asm_32.patinfly.domain.repository.IBikeRepository
import cat.deim.asm_32.patinfly.data.datasource.database.BikeDatasource
import cat.deim.asm_32.patinfly.data.datasource.database.model.BikeDTO
import cat.deim.asm_32.patinfly.data.datasource.remote.APIService
import cat.deim.asm_32.patinfly.data.datasource.remote.BikesResponse
import com.google.gson.GsonBuilder

class BikeRepository(
    private val bikeDao: BikeDatasource,
    private val apiService: APIService,
    ) : IBikeRepository {

    override suspend fun insert(bike: Bike): Boolean {
        val dto = BikeDTO.fromDomain(bike)
        return bikeDao.insert(dto) > 0
    }

    /*override suspend fun loadLocalData() {
        if (bikeLocalDataSource is BikeLocalDataSource) {
            bikeLocalDataSource.loadBikeData()
        }
        val localBikes = bikeLocalDataSource.getAll()
        if (localBikes.isNotEmpty()) {
            bikeDao.insertAll(localBikes.map { BikeDTO.fromDomain(it.toDomain()) })
            localBikes.map { it.toDomain() }
        }
    }*/

    override suspend fun getAll(token: String): Collection<Bike> {
        val dbBikes = bikeDao.getAll()
        return if (dbBikes.isNotEmpty()) {
            dbBikes.map { it.toDomain() }
        } else/* {
            *val response = apiService.getVehicles(token)
            val gson = GsonBuilder().setPrettyPrinting().create()
            val jsonString = gson.toJson(response)

            Log.d("API_RESPONSE", jsonString)
            val bikes = response.toDomain()

            if (bikes.isNotEmpty()) {
                bikeDao.insertAll(bikes.map { BikeDTO.fromDomain(it) })
                return bikes
            } else*/ {
                return emptyList()
            }
        }

    //}

    override suspend fun update(bike: Bike): Boolean {
        val dto = BikeDTO.fromDomain(bike)
        return bikeDao.update(dto) > 0
    }

    override suspend fun getById(uuid: String): Bike? {
        val dbBike = bikeDao.getById(uuid)
        return if (dbBike != null) {
            dbBike.toDomain()
        } else {
            return null

        }
    }

    override suspend fun delete(uuid: String): Boolean {
        return bikeDao.delete(uuid) > 0
    }
}