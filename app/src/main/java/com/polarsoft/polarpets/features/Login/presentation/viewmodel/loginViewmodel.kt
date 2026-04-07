package com.polarsoft.polarpets.features.Login.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.polarsoft.polarpets.features.Login.presentation.event.LoginEvent
import com.polarsoft.polarpets.features.Login.presentation.state.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnNameChange -> {
                _state.update {
                    it.copy(name = event.name)
                }
            }
            is LoginEvent.OnEmailChange -> {
                _state.update {
                    it.copy(email = event.email)
                }
            }
            is LoginEvent.OnPasswordChange -> {
                _state.update {
                    it.copy(password = event.password)
                }
            }
            LoginEvent.OnLoginClick -> {
                login()
            }
            LoginEvent.OnRegisterClick -> {
                register()
            }
        }
    }

    private fun login() {
        if (_state.value.email.isNotBlank() && _state.value.password.isNotBlank()) {
            _state.update { it.copy(isLoggedIn = true) }
        } else {
            _state.update { it.copy(error = "Por favor, completa todos los campos") }
        }
    }

    private fun register() {
        if (_state.value.name.isNotBlank() && _state.value.email.isNotBlank() && _state.value.password.isNotBlank()) {
            _state.update { it.copy(isLoggedIn = true) }
        } else {
            _state.update { it.copy(error = "Por favor, completa todos los campos") }
        }
    }
}