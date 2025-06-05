package cat.deim.asm_32.patinfly.presentation.profile

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import cat.deim.asm_32.patinfly.data.datasource.database.AppDatabase
import cat.deim.asm_32.patinfly.data.datasource.local.UserLocalDataSource
import cat.deim.asm_32.patinfly.data.repository.UserRepository
import cat.deim.asm_32.patinfly.domain.models.User
import cat.deim.asm_32.patinfly.ui.theme.PatinflyTheme
import kotlinx.coroutines.launch

class ProfileActivity : ComponentActivity() {

    private val tag = ProfileActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(tag, "ProfileActivity onCreate. Before setContent Execution")

        val sharedPrefs = getSharedPreferences("session", Context.MODE_PRIVATE)
        val uuid = sharedPrefs.getString("uuid", null)

        var user: User? = null

        lifecycleScope.launch {
            if (uuid != null) {
                val userDao = AppDatabase.getDatabase(applicationContext).userDatasource()
                val userRepository = UserRepository(userDao)
                user = userRepository.getById(uuid)
            }

            setContent {
                PatinflyTheme {
                    user?.let {
                        ProfileScreen(usuari = it)
                    }
                }
            }
            Log.d(tag, "After setContent Execution")
        }
    }
}