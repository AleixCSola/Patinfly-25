package cat.deim.asm_32.patinfly.presentation.bikes

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import cat.deim.asm_32.patinfly.data.datasource.database.AppDatabase
import cat.deim.asm_32.patinfly.data.datasource.local.SystemPricingPlanDataSource
import cat.deim.asm_32.patinfly.data.datasource.remote.BikeAPIDataSource
import cat.deim.asm_32.patinfly.data.repository.BikeRepository
import cat.deim.asm_32.patinfly.data.repository.SystemPricingPlanRepository
import cat.deim.asm_32.patinfly.domain.usecase.BikeRentDetailUseCase
import cat.deim.asm_32.patinfly.domain.usecase.UpdateBikeUseCase
import cat.deim.asm_32.patinfly.ui.theme.PatinflyTheme

class BikeRentDetailActivity : ComponentActivity() {

    private lateinit var viewModel: BikeRentDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bikeUuid = intent.getStringExtra("bike_uuid") ?: ""
        val sharedPrefs = getSharedPreferences("session", Context.MODE_PRIVATE)
        val token = sharedPrefs.getString("token", null) ?: ""
        val userUuid = sharedPrefs.getString("uuid", null) ?: ""
        val db = AppDatabase.getDatabase(applicationContext)
        val bikeDao = db.bikeDatasource()
        val planDao = db.systemPricingPlanDatasource()
        val planLocalDataSource = SystemPricingPlanDataSource.getInstance(applicationContext)
        val systemPricingPlanRepository = SystemPricingPlanRepository(planDao, planLocalDataSource)
        val apiService = BikeAPIDataSource.getService()
        val repository = BikeRepository(bikeDao, apiService)

        val rentUseCase = BikeRentDetailUseCase(repository, systemPricingPlanRepository, token)
        val updateBikeUseCase = UpdateBikeUseCase(repository, token)
        val factory = BikeRentDetailViewModelFactory(bikeUuid, userUuid, rentUseCase, systemPricingPlanRepository, updateBikeUseCase)
        viewModel = ViewModelProvider(this, factory)[BikeRentDetailViewModel::class.java]
        setContent {
            PatinflyTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    BikeRentDetailScreen(viewModel)
                }
            }
        }
    }
}
