package com.polarsoft.polarpets.features.Calendario.Presentation.Event


sealed class CalendarioEvent {

    data class OnDateSelected(val day: Int) : CalendarioEvent()

    object OnNextMonth : CalendarioEvent()
    object OnPrevMonth : CalendarioEvent()

    object OnCloseDialog : CalendarioEvent()
}