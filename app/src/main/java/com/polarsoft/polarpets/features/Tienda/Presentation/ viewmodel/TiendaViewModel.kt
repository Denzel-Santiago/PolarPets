package com.polarsoft.polarpets.FeaturesTiendaPresentatio.viewmodel


import androidx.lifecycle.ViewModel
import com.polarsoft.polarpets.features.Tienda.Presentation.State.TiendaState
import com.polarsoft.polarpets.features.Tienda.Presentation.event.TiendaEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class TiendaViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(TiendaState())
    val state: StateFlow<TiendaState> = _state

    fun onEvent(event: TiendaEvent) {
        when (event) {

            is TiendaEvent.OnMascotaClick -> {
                println("Mascota seleccionada: ${event.index}")
            }

            is TiendaEvent.OnTrajeClick -> {
                println("Traje seleccionado: ${event.index}")
            }
        }
    }
}