package cat.deim.asm_32.patinfly.presentation.bikes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cat.deim.asm_32.patinfly.data.datasource.local.BikeLocalDataSource
import cat.deim.asm_32.patinfly.data.repository.BikeRepository
import cat.deim.asm_32.patinfly.domain.usecase.BikeListUseCase
import cat.deim.asm_32.patinfly.ui.theme.PatinflyTheme

class BikeListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dataSource = BikeLocalDataSource.getInstance(applicationContext)
        val repository = BikeRepository(dataSource)
        val useCase = BikeListUseCase(repository)
        val viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return BikeViewModel(useCase) as T
            }
        })[BikeViewModel::class.java]

        setContent {
            PatinflyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    BikeListScreen(viewModel)
                }
            }
        }
    }
}