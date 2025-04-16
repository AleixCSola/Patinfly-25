package cat.deim.asm_32.patinfly.presentation.bikes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import cat.deim.asm_32.patinfly.domain.models.Bike
import cat.deim.asm_32.patinfly.domain.models.BikeType
import cat.deim.asm_32.patinfly.ui.theme.PatinflyTheme

import java.util.Date

class BikeListActivity : ComponentActivity() {
    private val bikeViewModel: BikeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PatinflyTheme {
                BikeListScreen(viewModel = bikeViewModel)
            }
        }
    }
}