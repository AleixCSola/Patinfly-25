package cat.deim.asm_32.patinfly.presentation.profile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import cat.deim.asm_32.patinfly.data.datasource.local.UserLocalDataSource
import cat.deim.asm_32.patinfly.ui.theme.PatinflyTheme


class ProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val user = UserLocalDataSource.getInstance(applicationContext).getUser()

        setContent {
            PatinflyTheme {
                user?.let {
                    ProfileScreen(user = it)
                }
            }
        }
    }
}