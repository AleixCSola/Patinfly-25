package cat.deim.asm_32.patinfly.presentation.splash

import android.app.Activity
import android.content.Intent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cat.deim.asm_32.patinfly.presentation.login.LoginActivity
import kotlinx.coroutines.delay
import cat.deim.asm_32.patinfly.ui.theme.Nunito


@Composable
fun SplashScreen() {
    val context = LocalContext.current

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.primary
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Patinfly",
                style = MaterialTheme.typography.displayLarge.copy(
                    fontFamily = Nunito,
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Explore urban areas on two wheels",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
    LaunchedEffect(Unit) {
        delay(2000)
        context.startActivity(Intent(context, LoginActivity::class.java))
        (context as Activity).finish()
    }
}
