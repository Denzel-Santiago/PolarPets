package com.polarsoft.polarpets.features.Calendario.Presentation.viewmodel


import androidx.lifecycle.ViewModel
import com.polarsoft.polarpets.features.Calendario.Presentation.Event.CalendarioEvent
import com.polarsoft.polarpets.features.Calendario.Presentation.state.CalendarioState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class CalendarioViewModel : ViewModel() {

    private val _state = MutableStateFlow(CalendarioState())
    val state: StateFlow<CalendarioState> = _state

    fun onEvent(event: CalendarioEvent) {
        when (event) {

            is CalendarioEvent.OnDateSelected -> {
                _state.update {
                    it.copy(
                        selectedDay = event.day,
                        showDialog = true
                    )
                }
            }

            CalendarioEvent.OnNextMonth -> {
                _state.update {
                    it.copy(currentMonth = it.currentMonth.plusMonths(1))
                }
            }

            CalendarioEvent.OnPrevMonth -> {
                _state.update {
                    it.copy(currentMonth = it.currentMonth.minusMonths(1))
                }
            }

            CalendarioEvent.OnCloseDialog -> {
                _state.update {
                    it.copy(showDialog = false)
                }
            }
        }
    }
}