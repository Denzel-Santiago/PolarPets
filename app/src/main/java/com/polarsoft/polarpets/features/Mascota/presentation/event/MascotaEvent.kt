package com.polarsoft.polarpets.features.Mascota.presentation.event

sealed class MascotaEvent {
    object OnEditarClick : MascotaEvent()
    object OnBottomCardClick : MascotaEvent()
}