package cat.deim.asm_32.patinfly.presentation.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cat.deim.asm_32.patinfly.ui.theme.PatinflyTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import cat.deim.asm_32.patinfly.ui.theme.Nunito


@Composable
fun MainScreen(
    perfilClick: () -> Unit,
    bicisClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Patinfly",
            style = MaterialTheme.typography.displayLarge.copy(
                fontFamily = Nunito,
                fontWeight = FontWeight.Bold
            ),
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(8.dp))

        Image(
            painter = painterResource(id = cat.deim.asm_32.patinfly.R.drawable.bike_splash_screen),
            contentDescription = "Logo Patinfly",
            modifier = Modifier
                .height(360.dp)
                .clip(RoundedCornerShape(24.dp))
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = perfilClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(text = "Ver perfil")
        }

        Button(
            onClick = bicisClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(text = "Ver bicis")
        }
    }
}
@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    PatinflyTheme {
        MainScreen(
            perfilClick = {},
            bicisClick = {}
        )
    }
}