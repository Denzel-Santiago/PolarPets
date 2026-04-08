package com.polarsoft.polarpets.FeaturesTiendaPresentatio.viewmodel


import androidx.compose.runtime.currentComposer
import androidx.lifecycle.ViewModel
import com.polarsoft.polarpets.features.Tienda.Presentation.State.TiendaState
import com.polarsoft.polarpets.features.Tienda.Presentation.event.TiendaEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
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

            is TiendaEvent.OnComprarClick -> {
                comprarTraje(event.id)
            }
        }
    }

    private fun comprarTraje(id: Int) {
        _state.update { current ->
            current.copy(
                trajes = current.trajes.map {
                    if (it.id == id && !it.esPremium) {
                        it.copy(comprado = true)
                    } else it
                }
            )
        }
    }
}