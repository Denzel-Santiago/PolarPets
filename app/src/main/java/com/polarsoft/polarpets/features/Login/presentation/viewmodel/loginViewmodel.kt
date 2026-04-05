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
        }
    }

    private fun login() {
        println("Email: ${_state.value.email}")
        println("Password: ${_state.value.password}")
    }
}