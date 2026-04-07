package com.polarsoft.polarpets.features.Tienda.Presentation.event

sealed class TiendaEvent{
    data class OnMascotaClick(val index: Int) : TiendaEvent()
    data class OnTrajeClick(val index: Int) : TiendaEvent()
}