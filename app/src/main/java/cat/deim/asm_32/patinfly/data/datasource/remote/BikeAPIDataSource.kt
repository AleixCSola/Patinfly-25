package cat.deim.asm_32.patinfly.data.datasource.remote

import android.annotation.SuppressLint
import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
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
                    val logging = HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.HEADERS
                    }

                    val client = OkHttpClient.Builder()
                        .addInterceptor(logging)
                        .build()

                    retrofit = Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .client(client)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                        .create(APIService::class.java)
                }
            }

        fun getService(): APIService = retrofit
    }
}