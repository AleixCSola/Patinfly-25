package cat.deim.asm_32.patinfly.data.datasource.remote

import android.annotation.SuppressLint
import android.content.Context
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BikeAPIDataSource private constructor() {

    private var context: Context? = null

    companion object {
        private const val BASE_URL = "https://api.patinfly.dev/api/"
        private lateinit var retrofit: APIService

        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var instance: BikeAPIDataSource? = null

        fun getInstance(context: Context)=
            instance ?: synchronized(this) {
                instance ?: BikeAPIDataSource().also {
                    instance = it
                    it.context = context
                    retrofit = Retrofit.Builder()
                    //addConverterFactory(Json.asConverterFactory(“application/json”.toMediaType()))
                    //.addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .build().create(APIService::class.java)
                }
            }

        fun getService(): APIService = retrofit
    }
}