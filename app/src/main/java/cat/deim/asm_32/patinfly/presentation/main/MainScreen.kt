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
            modifier = Modifier.padding(bottom = 32.dp)
        )

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