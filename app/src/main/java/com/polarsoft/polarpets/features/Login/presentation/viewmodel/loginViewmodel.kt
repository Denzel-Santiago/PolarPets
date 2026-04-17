package com.polarsoft.polarpets.features.Login.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polarsoft.polarpets.features.Login.domain.repository.LoginRepository
import com.polarsoft.polarpets.features.Login.presentation.event.LoginEvent
import com.polarsoft.polarpets.features.Login.presentation.state.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnNameChange ->
                _state.update { it.copy(name = event.name, error = null) }

            is LoginEvent.OnEmailChange ->
                _state.update { it.copy(email = event.email, error = null) }

            is LoginEvent.OnPasswordChange ->
                _state.update { it.copy(password = event.password, error = null) }

            LoginEvent.OnLoginClick -> login()
            LoginEvent.OnRegisterClick -> register()
        }
    }

    private fun login() {
        val email = _state.value.email.trim()
        val password = _state.value.password

        // Validaciones locales
        if (email.isBlank() || password.isBlank()) {
            _state.update { it.copy(error = "Completa todos los campos") }
            return
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _state.update { it.copy(error = "Correo electrónico inválido") }
            return
        }
        if (password.length < 6) {
            _state.update { it.copy(error = "La contraseña debe tener al menos 6 caracteres") }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            loginRepository.login(email, password)
                .onSuccess {
                    _state.update { it.copy(isLoading = false, isLoggedIn = true) }
                }
                .onFailure { error ->
                    _state.update {
                        it.copy(isLoading = false, error = error.message)
                    }
                }
        }
    }

    private fun register() {
        val name = _state.value.name.trim()
        val email = _state.value.email.trim()
        val password = _state.value.password

        // Validaciones locales
        if (name.isBlank() || email.isBlank() || password.isBlank()) {
            _state.update { it.copy(error = "Completa todos los campos") }
            return
        }
        if (name.length < 3) {
            _state.update { it.copy(error = "El nombre debe tener al menos 3 caracteres") }
            return
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _state.update { it.copy(error = "Correo electrónico inválido") }
            return
        }
        if (password.length < 6) {
            _state.update { it.copy(error = "La contraseña debe tener al menos 6 caracteres") }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            loginRepository.register(name, email, password)
                .onSuccess {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            successMessage = "¡Registro exitoso! Inicia sesión"
                        )
                    }
                }
                .onFailure { error ->
                    _state.update {
                        it.copy(isLoading = false, error = error.message)
                    }
                }
        }
    }
}
