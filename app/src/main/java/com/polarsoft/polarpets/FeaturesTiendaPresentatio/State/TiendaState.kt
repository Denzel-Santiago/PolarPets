package com.polarsoft.polarpets.FeaturesTiendaPresentatio.State

import com.polarsoft.polarpets.R

data class TiendaState(
    val mascota: List<Int> = listOf(
        R.drawable.foquita
    ),
    val trajes: List<Int> = listOf(
        R.drawable.pinguino
    )
)