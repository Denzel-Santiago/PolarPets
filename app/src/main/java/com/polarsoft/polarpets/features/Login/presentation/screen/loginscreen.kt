package com.polarsoft.polarpets.features.Login.presentation.screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.polarsoft.polarpets.R
import com.polarsoft.polarpets.features.Login.presentation.event.LoginEvent
import com.polarsoft.polarpets.features.Login.presentation.state.LoginState
import com.polarsoft.polarpets.features.Login.presentation.viewmodel.LoginViewModel


@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel(),
    onNavigateToTienda: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    var isLoginMode by remember { mutableStateOf(true) }

    LaunchedEffect(state.isLoggedIn) {
        if (state.isLoggedIn) {
            onNavigateToTienda()
        }
    }

    AnimatedContent(
        targetState = isLoginMode,
        modifier = modifier,
        transitionSpec = {
            slideInHorizontally { width -> width } togetherWith
                    slideOutHorizontally { width -> -width }
        }
    ) { target ->

        if (target) {
            LoginContent(
                state = state,
                onEvent = viewModel::onEvent,
                onSwitchToRegister = {
                    isLoginMode = false
                }
            )
        } else {
            RegisterContent(
                state = state,
                onEvent = viewModel::onEvent,
                onSwitchToLogin = {
                    isLoginMode = true
                }
            )
        }
    }
}


@Composable
fun LoginContent(
    state: LoginState,
    onEvent: (LoginEvent) -> Unit,
    onSwitchToRegister: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.clouds),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Box(
            modifier = Modifier
                .fillMaxHeight(0.9f)
                .fillMaxWidth(0.85f)
                .align(Alignment.Center)
                .clip(RoundedCornerShape(32.dp))
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF3D67A5).copy(alpha = 0.88f),
                            Color(0xFF193050).copy(alpha = 0.88f)
                        )
                    )
                )
                .border(
                    1.dp,
                    Color.White.copy(alpha = 0.2f),
                    RoundedCornerShape(32.dp)
                )
                .padding(24.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "POLAR PETS",
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(24.dp))
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier.size(200.dp)
                )
                Spacer(modifier = Modifier.height(32.dp))

                CustomInput(
                    value = state.email,
                    onValueChange = { onEvent(LoginEvent.OnEmailChange(it)) },
                    placeholder = "Correo"
                )
                Spacer(modifier = Modifier.height(16.dp))
                CustomInput(
                    value = state.password,
                    onValueChange = { onEvent(LoginEvent.OnPasswordChange(it)) },
                    placeholder = "Contraseña",
                    isPassword = true
                )

                if (state.error != null) {
                    Text(text = state.error, color = Color.Red, modifier = Modifier.padding(top = 8.dp))
                }

                Spacer(modifier = Modifier.height(24.dp))
                CustomButton(
                    text = "Iniciar Sesión",
                    onClick = { onEvent(LoginEvent.OnLoginClick) }
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "¿No tienes cuenta? Regístrate Aquí",
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.clickable { onSwitchToRegister() },
                    color = Color.White.copy(alpha = 0.8f)
                )
            }
        }
    }
}

@Composable
fun CustomInput(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    isPassword: Boolean = false
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0xFFD9D9D9))
            .padding(horizontal = 14.dp, vertical = 10.dp)
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            visualTransformation = if (isPassword) PasswordVisualTransformation() else androidx.compose.ui.text.input.VisualTransformation.None,
            textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color(0xFF3A6EA5))
        )
        if (value.isEmpty()) {
            Text(text = placeholder, color = Color(0xFF3A6EA5).copy(alpha = 0.7f))
        }
    }
}

@Composable
fun CustomButton(
    text: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0xFF396DC5))
            .clickable { onClick() }
            .padding(vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, color = Color.White, style = MaterialTheme.typography.labelLarge)
    }
}

@Composable
fun RegisterContent(
    state: LoginState,
    onEvent: (LoginEvent) -> Unit,
    onSwitchToLogin: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.clouds),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Box(
            modifier = Modifier
                .fillMaxHeight(0.9f)
                .fillMaxWidth(0.85f)
                .align(Alignment.Center)
                .clip(RoundedCornerShape(32.dp))
                .background(Color(0xFF6DA8E5).copy(alpha = 0.25f))
                .border(1.dp, Color.White.copy(alpha = 0.3f), RoundedCornerShape(32.dp))
                .padding(24.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "POLAR PETS",
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color.White,
                    modifier = Modifier.alpha(0.9f)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Registro", style = MaterialTheme.typography.titleLarge, color = Color(0xFF2F4F6F))
                Spacer(modifier = Modifier.height(32.dp))

                CustomInputRegister(
                    value = state.name,
                    onValueChange = { onEvent(LoginEvent.OnNameChange(it)) },
                    placeholder = "NOMBRE"
                )
                Spacer(modifier = Modifier.height(16.dp))
                CustomInputRegister(
                    value = state.email,
                    onValueChange = { onEvent(LoginEvent.OnEmailChange(it)) },
                    placeholder = "CORREO"
                )
                Spacer(modifier = Modifier.height(16.dp))
                CustomInputRegister(
                    value = state.password,
                    onValueChange = { onEvent(LoginEvent.OnPasswordChange(it)) },
                    placeholder = "CONTRASEÑA",
                    isPassword = true
                )

                if (state.error != null) {
                    Text(text = state.error, color = Color.Red, modifier = Modifier.padding(top = 8.dp))
                }

                Spacer(modifier = Modifier.height(28.dp))
                RegisterButton(
                    text = "Registrar",
                    onClick = { onEvent(LoginEvent.OnRegisterClick) }
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "¿Ya tienes cuenta? Iniciá Sesión",
                    style = MaterialTheme.typography.labelLarge,
                    color = Color(0xFF2F4F6F),
                    modifier = Modifier.clickable { onSwitchToLogin() }
                )
            }
        }
    }
}

@Composable
fun CustomInputRegister(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    isPassword: Boolean = false
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(8.dp, RoundedCornerShape(20.dp))
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0xFFE3EDF7))
            .padding(horizontal = 16.dp, vertical = 14.dp)
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            visualTransformation = if (isPassword) PasswordVisualTransformation() else androidx.compose.ui.text.input.VisualTransformation.None,
            textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color(0xFF2F4F6F))
        )
        if (value.isEmpty()) {
            Text(text = placeholder, color = Color(0xFF2F4F6F).copy(alpha = 0.6f))
        }
    }
}

@Composable
fun RegisterButton(
    text: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(10.dp, RoundedCornerShape(20.dp))
            .clip(RoundedCornerShape(20.dp))
            .background(
                Brush.horizontalGradient(
                    colors = listOf(Color(0xFF5B9CFF), Color(0xFF396DC5))
                )
            )
            .clickable { onClick() }
            .padding(vertical = 14.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, color = Color.White, style = MaterialTheme.typography.labelLarge)
    }
}