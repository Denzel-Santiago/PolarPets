package com.polarsoft.polarpets.features.Calendario.Presentation.state

import java.time.YearMonth

data class DiaHabito(
    val day: Int,
    val retos: List<String>
)

data class CalendarioState(
    val currentMonth: YearMonth = YearMonth.now(),
    val selectedDay: Int? = null,
    val showDialog: Boolean = false,

    val puntos: Int = 480,
    val racha: Int = 5,
    val diasSemanaActivos: List<Int> = listOf(1,2,3,4,5),

    val historial: List<DiaHabito> = listOf(
        DiaHabito(2, listOf("Caminar", "Agua")),
        DiaHabito(5, listOf("Leer")),
        DiaHabito(13, listOf("Caminar", "Leer", "Agua"))
    )
)