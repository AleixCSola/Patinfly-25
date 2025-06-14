package cat.deim.asm_32.patinfly.presentation.login

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cat.deim.asm_32.patinfly.R
import cat.deim.asm_32.patinfly.data.datasource.database.AppDatabase
import cat.deim.asm_32.patinfly.domain.usecase.LoginUseCase
import cat.deim.asm_32.patinfly.presentation.main.MainActivity
import cat.deim.asm_32.patinfly.data.datasource.local.UserLocalDataSource
import cat.deim.asm_32.patinfly.data.datasource.remote.BikeAPIDataSource
import cat.deim.asm_32.patinfly.data.repository.UserRepository
import cat.deim.asm_32.patinfly.ui.theme.Nunito
import cat.deim.asm_32.patinfly.domain.models.Credentials
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Composable
fun LoginScreen(loginUsecase: LoginUseCase?) {
    val coroutineScope = rememberCoroutineScope()
    Surface {
        Column(modifier = Modifier.width(300.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally){
            var credentials by remember { mutableStateOf(Credentials(email = "", password = "")) }
            val context = LocalContext.current
            Row(modifier = Modifier.padding(vertical = 8.dp)) {
                TextField(
                    value =credentials.email,
                    label = { Text(text = "Username") },
                    onValueChange = {
                            data -> credentials = credentials.copy(email = data)
                    },)
            }
            Row(modifier = Modifier.padding(vertical = 8.dp)) {
                TextField(
                    value = credentials.password,
                    label = { Text(text = "Password") },
                    onValueChange = { data -> credentials = credentials.copy(password = data) },
                    visualTransformation = PasswordVisualTransformation()
                )
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally){
                Row {
                    Button(
                        modifier = Modifier.width(200.dp),
                        content = { Text(text = "Login") },
                        enabled = credentials.isNotEmpty(),
                        onClick = { coroutineScope.launch {
                            withContext(Dispatchers.IO) {
                                val userIsValidated = loginUsecase!!.execute(credentials, context)
                                if (userIsValidated) {
                                    val intent: Intent = Intent()
                                    intent.setClass(context, MainActivity::class.java)
                                    context.startActivity(intent)
                                }
                            }
                        }
                        })
                }
                Row {
                    Button( modifier = Modifier.width(200.dp),
                        content = { Text(text = "Register") },
                        onClick = { /*TODO*/ })
                }
            }
        }
    }
}