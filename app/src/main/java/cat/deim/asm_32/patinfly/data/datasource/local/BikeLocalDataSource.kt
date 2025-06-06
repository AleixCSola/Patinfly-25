package cat.deim.asm_32.patinfly.data.datasource.local

/*import android.annotation.SuppressLint
import android.content.Context
import cat.deim.asm_32.patinfly.data.datasource.IBikeDataSource
import cat.deim.asm_32.patinfly.data.datasource.remote.BikesResponse
import cat.deim.asm_32.patinfly.domain.models.Bike
import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.json.Json
import java.io.IOException
import java.io.InputStreamReader

class BikeLocalDataSource private constructor() : IBikeDataSource {

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var instance: BikeLocalDataSource? = null

        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: BikeLocalDataSource().also {
                    it.context = context
                    instance = it
                }
            }
    }

    private var bicis: MutableList<Bike> = mutableListOf()
    private var context: Context? = null

    override fun getAll(): List<Bike> {
        if (bicis.isEmpty()) {
            loadBikeData()
        }
        return bicis
    }

    private fun loadBikeData() {
        try {
            context?.assets?.open("bikes.json")?.use { inputStream ->
                InputStreamReader(inputStream).use { reader ->
                    val json = reader.readText()
                    bicis = parseJson(json)?.toMutableList() ?: mutableListOf()
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun parseJson(json: String): List<Bike>? {
        return try {
            val bikesResponse = Json {
                ignoreUnknownKeys = true // Por si el JSON tiene campos no mapeados
            }.decodeFromString<BikesResponse>(json)
            bikesResponse.toDomain()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
    /*override fun insert(bike:BikeModel):Boolean{
        bicis.add(bike)
        return true
    }

    override fun insertOrUpdate(bike: BikeModel): Boolean {
        val i=bicis.indexOfFirst{it.uuid==bike.uuid}
        return if (i==-1) {
            insert(bike)
        }else{
            update(bike)!=null
        }
    }
    override fun getAll():List<BikeModel> =bicis.toList()
    override fun getById(uuid: String): BikeModel? {
        return bicis.firstOrNull{it.uuid==uuid}
    }
    override fun update(bike: BikeModel): BikeModel? {
        val i = bicis.indexOfFirst { it.uuid == bike.uuid }
        return if (i != -1) {
            val antigua = bicis[i]
            bicis[i] = bike
            antigua
        } else {
            null
        }
    }
    override fun delete(uuid: String): Boolean {
        val elim = bicis.any {it.uuid == uuid }
        bicis.removeIf { it.uuid == uuid}
        return elim
    }*/
*/