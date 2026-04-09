package com.polarsoft.polarpets.features.Tienda.Presentation.State

import com.polarsoft.polarpets.R


data class Traje(
    val id: Int,
    val Image: Int,
    val nombre: String,
    val esPremium: Boolean = false,
    val comprado: Boolean = false
)

data class TiendaState(
    val mascota: List<Int> = listOf(
        R.drawable.foquita,
        R.drawable.pinguino
    ),
    val trajes: List<Traje> = listOf(

        // 👕 5 NORMALES
        Traje(1, R.drawable.normal1,"Normal 1"),
        Traje(2, R.drawable.normal2, "Normal 2"),
        Traje(3, R.drawable.normal3, "Normal 3"),
        Traje(4, R.drawable.normal4, "Normal 4"),
        Traje(5, R.drawable.normal5, "Normal 5"),
        Traje(id = 6,R.drawable.normal6,"Normal6"),
        Traje(id = 7 ,R.drawable.normal7,"Normal7"),
        Traje(id = 8, R.drawable.normal8,"Normal8"),

        Traje(6, R.drawable.premium1, "Premium 1", true),
        Traje(7, R.drawable.premium2, "Premium 2", true),
        Traje(8, R.drawable.premium3, "Premium 3", true),
        Traje(9, R.drawable.premium4, "Premium 4", true),
        Traje(10, R.drawable.premium5, "Premium 5", true),
        Traje(11, R.drawable.premium6, "Premium 6", true),
        Traje(12, R.drawable.premium7, "Premium 7", true),
        Traje(13, R.drawable.premium8, "Premium 8", true),
        Traje(14, R.drawable.premium9, "Premium 9", true),
    )
)
