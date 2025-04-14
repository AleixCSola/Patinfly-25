package cat.deim.asm_32.patinfly.presentation.login

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cat.deim.asm_32.patinfly.domain.usecase.LoginUseCase

import cat.deim.asm_32.patinfly.presentation.main.MainActivity
import cat.deim.asm_32.patinfly.data.datasource.local.UserLocalDataSource
import cat.deim.asm_32.patinfly.data.repository.UserRepository


@Composable
fun UserLoginForm(loginUseCase: LoginUseCase) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var Error by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .width(300.dp)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    try {
                        val success = loginUseCase.execute(email, password)
                        if (success) {
                            val intent = Intent(context, MainActivity::class.java).apply {
                                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            }
                            context.startActivity(intent)
                        } else {
                            Error = true
                        }
                    } catch (e: Exception) {
                        Error = true
                        Log.e("LOGIN", "Error", e)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = email.isNotBlank() && password.isNotBlank()
            ) {
                Text("Login")
            }
            if (Error) {
                Text(
                    text = "Email o contraseña incorrectos",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun PreviewUserLoginForm() {
    val context = LocalContext.current
    UserLoginForm(
        loginUseCase = LoginUseCase(
            UserRepository(
                UserLocalDataSource.getInstance(context)
            )
        )
    )
}