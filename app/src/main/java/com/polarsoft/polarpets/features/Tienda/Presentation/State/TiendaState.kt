package com.polarsoft.polarpets.features.Tienda.Presentation.State

import com.polarsoft.polarpets.R

data class Traje(
    val id: Int,
    val Image: Int,
    val nombre: String,
    val esPremium: Boolean=false,
    val comprado: Boolean=false
)

data class TiendaState(
    val mascota: List<Int> = listOf(
        R.drawable.foquita
    ),
    val trajes: List<Traje> = listOf(
        Traje(1,R.drawable.pinguino,"bebe"),
        Traje(2, R.drawable.foquita,"bebe1"),
        Traje(3,R.drawable.leonf,"of", esPremium = true),
        Traje(4,R.drawable.logo,"LOGO", esPremium = true)
    )
    )
