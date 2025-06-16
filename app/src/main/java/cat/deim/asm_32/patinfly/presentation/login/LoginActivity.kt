package cat.deim.asm_32.patinfly.presentation.login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.ui.platform.LocalContext
import cat.deim.asm_32.patinfly.data.repository.UserRepository
import cat.deim.asm_32.patinfly.domain.usecase.LoginUseCase
import cat.deim.asm_32.patinfly.ui.theme.PatinflyTheme
import cat.deim.asm_32.patinfly.data.datasource.database.AppDatabase
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import cat.deim.asm_32.patinfly.data.datasource.remote.BikeAPIDataSource


class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BikeAPIDataSource.getInstance(applicationContext)
        setContent {
            PatinflyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val apiService = BikeAPIDataSource.getService()
                    LoginScreen(
                        LoginUseCase(
                            UserRepository(
                                AppDatabase.getDatabase(LocalContext.current).userDatasource(),
                                apiService
                            ),
                            apiService
                        )
                    )
                }
            }
        }
    }
}