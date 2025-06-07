package cat.deim.asm_32.patinfly.presentation.bikes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cat.deim.asm_32.patinfly.data.datasource.database.AppDatabase
import cat.deim.asm_32.patinfly.data.datasource.local.SystemPricingPlanDataSource
import cat.deim.asm_32.patinfly.data.datasource.remote.BikeAPIDataSource
import cat.deim.asm_32.patinfly.data.repository.BikeRepository
import cat.deim.asm_32.patinfly.data.repository.SystemPricingPlanRepository
import cat.deim.asm_32.patinfly.domain.usecase.BikeRentDetailUseCase
import cat.deim.asm_32.patinfly.ui.theme.PatinflyTheme

class BikeRentDetailActivity : ComponentActivity() {

    private lateinit var viewModel: BikeRentDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bikeUuid = intent.getStringExtra("bike_uuid") ?: ""
        val userId = intent.getStringExtra("user_id") ?: ""
        val bikeDao = AppDatabase.getDatabase(applicationContext).bikeDatasource()
        val planDao = AppDatabase.getDatabase(applicationContext).systemPricingPlanDatasource()
        val planLocalDataSource = SystemPricingPlanDataSource.getInstance(applicationContext)
        val systemPricingPlanRepository = SystemPricingPlanRepository(planDao, planLocalDataSource)
        val apiService = BikeAPIDataSource.getService()
        val repository = BikeRepository(bikeDao, apiService)
        val rentUseCase = BikeRentDetailUseCase(repository, systemPricingPlanRepository)
        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return BikeRentDetailViewModel(bikeUuid, userId, rentUseCase, systemPricingPlanRepository) as T
            }
        })[BikeRentDetailViewModel::class.java]

        setContent {
            PatinflyTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    BikeRentDetailScreen(viewModel)
                }
            }
        }
    }
}