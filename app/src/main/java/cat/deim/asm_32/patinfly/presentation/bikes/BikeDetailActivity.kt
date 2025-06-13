package cat.deim.asm_32.patinfly.presentation.bikes

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cat.deim.asm_32.patinfly.data.repository.BikeRepository
import cat.deim.asm_32.patinfly.domain.usecase.BikeListUseCase
import cat.deim.asm_32.patinfly.ui.theme.PatinflyTheme
import cat.deim.asm_32.patinfly.presentation.profile.ProfileActivity
import cat.deim.asm_32.patinfly.data.datasource.database.AppDatabase
import cat.deim.asm_32.patinfly.data.datasource.remote.BikeAPIDataSource
import android.content.Context
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import cat.deim.asm_32.patinfly.domain.usecase.BikeDetailUseCase
import cat.deim.asm_32.patinfly.domain.usecase.UpdateBikeUseCase

class BikeDetailActivity : ComponentActivity() {

    private val bikeDetailViewModel: BikeDetailViewModel by viewModels {
        val bikeDao = AppDatabase.getDatabase(applicationContext).bikeDatasource()
        val apiService = BikeAPIDataSource.getService()
        val bikeRepository = BikeRepository(bikeDao, apiService)
        val bikeUuid = intent.getStringExtra("bike_uuid") ?: ""
        val sharedPrefs = getSharedPreferences("session", Context.MODE_PRIVATE)
        val token = sharedPrefs.getString("token", null) ?: ""
        BikeDetailViewModelFactory(
            bikeUuid,
            BikeDetailUseCase(bikeRepository),
            UpdateBikeUseCase(bikeRepository, token)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PatinflyTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val bike by bikeDetailViewModel.bike.collectAsState()

                    if (bike == null) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    } else {
                        BikeDetailScreen(
                            viewModel = bikeDetailViewModel,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}