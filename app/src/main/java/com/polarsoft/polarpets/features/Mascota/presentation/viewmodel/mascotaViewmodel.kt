package com.polarsoft.polarpets.features.Mascota.presentation.viewmodel


import androidx.lifecycle.ViewModel
import com.polarsoft.polarpets.features.Mascota.domain.Mascota
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.polarsoft.polarpets.R



@HiltViewModel
class MascotaViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(
        Mascota(
            nombre = "Leon F Kennedy",
            puntosActuales = 557,
            puntosMaximos = 700,
            nivel = 3,
            imagen = R.drawable.leonf
        )
    )
    val state: StateFlow<Mascota> = _state

    fun onEvent(event: com.polarsoft.polarpets.features.Mascota.presentation.event.MascotaEvent) {
        when (event) {
            is com.polarsoft.polarpets.features.Mascota.presentation.event.MascotaEvent.OnEditarClick -> {
                // lógica futura
            }

            is com.polarsoft.polarpets.features.Mascota.presentation.event.MascotaEvent.OnBottomCardClick -> {
                // lógica futura
            }
        }
    }
}