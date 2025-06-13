package cat.deim.asm_32.patinfly.data.repository

import android.util.Log
import cat.deim.asm_32.patinfly.domain.models.Bike
import cat.deim.asm_32.patinfly.domain.repository.IBikeRepository
import cat.deim.asm_32.patinfly.data.datasource.database.BikeDatasource
import cat.deim.asm_32.patinfly.data.datasource.database.model.BikeDTO
import cat.deim.asm_32.patinfly.data.datasource.remote.APIService
import cat.deim.asm_32.patinfly.data.datasource.remote.toDomain
import com.google.gson.GsonBuilder

class BikeRepository(
    private val bikeDao: BikeDatasource,
    private val apiService: APIService,
    ) : IBikeRepository {

    override suspend fun insert(bike: Bike): Boolean {
        val dto = BikeDTO.fromDomain(bike)
        return bikeDao.insert(dto) > 0
    }

    override suspend fun getAll(token: String): Collection<Bike> {
        val dbBikes = bikeDao.getAll()
        return if (dbBikes.isNotEmpty()) {
            dbBikes.map { it.toDomain() }
        } else {
            val response = apiService.getVehicles(token)
            val gson = GsonBuilder().setPrettyPrinting().create()
            val jsonString = gson.toJson(response)

            Log.d("API_RESPONSE", jsonString)
            val bikes = response.vehicles.toDomain()

            if (bikes.isNotEmpty()) {
                bikeDao.insertAll(bikes.map { BikeDTO.fromDomain(it) })
                return bikes
            } else {
                return emptyList()
            }
        }

    }

    override suspend fun update(bike: Bike, operacio: Int, token: String): Boolean {
        val operacioOk = when (operacio) {
            0 -> reserve(bike, token)
            1 -> release(bike, token)
            2 -> start(bike, token)
            3 -> stop(bike, token)
            else -> false
        }

        if (!operacioOk) return false
        else {
            val dto = BikeDTO.fromDomain(bike)
            return bikeDao.update(dto) > 0
        }
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

    private suspend fun reserve(bike: Bike, token: String): Boolean {
        return try {
            val response = apiService.doReserve("Bearer $token", bike.uuid)

            val gson = GsonBuilder().setPrettyPrinting().create()
            val jsonString = gson.toJson(response)
            Log.d("API_RESPONSE", jsonString)

            response.success
        } catch (e: Exception) {
            Log.e("API_ERROR", "Reserva fallida", e)
            false
        }
    }

    private suspend fun release(bike: Bike, token: String): Boolean {
        return try {
            val response = apiService.doRelease("Bearer $token", bike.uuid)
            val gson = GsonBuilder().setPrettyPrinting().create()
            val jsonString = gson.toJson(response)
            Log.d("API_RESPONSE", jsonString)
            response.success
        } catch (e: Exception) {
            Log.e("API_ERROR", "Alliberació fallida", e)
            false
        }
    }

    private suspend fun start(bike: Bike, token: String): Boolean {
        return try {
            val response = apiService.doStart("Bearer $token", bike.uuid)
            val gson = GsonBuilder().setPrettyPrinting().create()
            Log.d("API_RESPONSE", gson.toJson(response))
            response.success
        } catch (e: Exception) {
            Log.e("API_ERROR", "Start fallido", e)
            false
        }
    }

    private suspend fun stop(bike: Bike, token: String): Boolean {
        return try {
            val response = apiService.doStop("Bearer $token", bike.uuid)
            val gson = GsonBuilder().setPrettyPrinting().create()
            Log.d("API_RESPONSE", gson.toJson(response))
            response.success
        } catch (e: Exception) {
            Log.e("API_ERROR", "Stop fallido", e)
            false
        }
    }
}