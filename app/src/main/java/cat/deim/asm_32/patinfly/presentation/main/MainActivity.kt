package cat.deim.asm_32.patinfly.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import android.content.Intent

import cat.deim.asm_32.patinfly.presentation.bikes.BikeListActivity
import cat.deim.asm_32.patinfly.presentation.profile.ProfileActivity
import cat.deim.asm_32.patinfly.ui.theme.PatinflyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PatinflyTheme {
                MainScreen(
                    perfilClick = {
                        startActivity(Intent(this, ProfileActivity::class.java))
                    },
                    bicisClick = {
                        startActivity(Intent(this, BikeListActivity::class.java))
                    }
                )
            }
        }
    }
}