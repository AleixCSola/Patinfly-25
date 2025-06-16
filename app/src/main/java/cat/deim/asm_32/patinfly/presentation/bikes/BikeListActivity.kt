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


class BikeListActivity : ComponentActivity() {

    private val tag = BikeListActivity::class.java.simpleName
    private lateinit var viewModel: BikeListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(tag, "BikeListActivity onCreate")
        val bikeDao = AppDatabase.getDatabase(applicationContext).bikeDatasource()
        val apiService = BikeAPIDataSource.getService()
        val sharedPrefs = getSharedPreferences("session", Context.MODE_PRIVATE)
        val token = sharedPrefs.getString("token", null)
        if (token != null){
            val repository = BikeRepository(bikeDao, apiService)
            val useCase = BikeListUseCase(repository, token)

            Log.d(tag, "Creant viewModel Bike...")

            viewModel=ViewModelProvider(this, object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return BikeListViewModel(useCase) as T
                }
            })[BikeListViewModel::class.java]

            Log.d(tag,"Before setContent Execution")
            setContent {
                PatinflyTheme {
                    Surface(modifier=Modifier.fillMaxSize()) {
                        BikeListScreen(
                            viewModel = viewModel,
                            onProfileClick = {
                                startActivity(Intent(this, ProfileActivity::class.java))
                            },
                            onBackClick = {
                                finish()
                            }
                        )
                    }
                }
            }
            Log.d(tag, "After setContent Execution")
        }
    }
    override fun onResume() {
        super.onResume()
        viewModel.loadBikes()  // <-- recarga lista cuando vuelves a esta activity
    }
}