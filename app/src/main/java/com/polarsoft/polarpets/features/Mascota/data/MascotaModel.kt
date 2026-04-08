package com.polarsoft.polarpets.features.Mascota.data

data class MascotaModel(
    val nombre: String = "Leon F Kennedy",
    val puntosActuales: Int = 557,
    val puntosMaximos: Int = 700,
    val nivel: Int = 3,
    val imagen: Int // por ahora drawable local
)