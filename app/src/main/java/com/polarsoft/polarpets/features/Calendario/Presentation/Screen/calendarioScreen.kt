package com.polarsoft.polarpets.features.Calendario.Presentation.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.polarsoft.polarpets.features.Calendario.Presentation.Event.CalendarioEvent
import com.polarsoft.polarpets.features.Calendario.Presentation.viewmodel.CalendarioViewModel
import java.time.format.TextStyle
import java.util.Locale
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.unit.sp
import com.polarsoft.polarpets.features.Calendario.Presentation.state.CalendarioState

@Composable
fun CalendarioScreen(
    viewModel: CalendarioViewModel = viewModel()
) {

    val state by viewModel.state.collectAsState()
    val daysInMonth = state.currentMonth.lengthOfMonth()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0A1929))
            .padding(16.dp)
    ) {

        HeaderPro(state)

        Spacer(modifier = Modifier.height(16.dp))

        // CALENDARIO CARD
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF102A43), RoundedCornerShape(20.dp))
                .padding(16.dp)
        ) {

            // HEADER MES
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = "${state.currentMonth.month.getDisplayName(TextStyle.FULL, Locale.getDefault())} ${state.currentMonth.year}",
                    color = Color.White
                )

                Row {
                    Text("<", color = Color.White,
                        modifier = Modifier.clickable {
                            viewModel.onEvent(CalendarioEvent.OnPrevMonth)
                        })

                    Spacer(modifier = Modifier.width(12.dp))

                    Text(">", color = Color.White,
                        modifier = Modifier.clickable {
                            viewModel.onEvent(CalendarioEvent.OnNextMonth)
                        })
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // DÍAS
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                listOf("L","M","X","J","V","S","D").forEach {
                    Text(it, color = Color.Gray, fontSize = 12.sp)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            LazyVerticalGrid(columns = GridCells.Fixed(7)) {

                items(daysInMonth) { index ->

                    val day = index + 1
                    val isSelected = state.selectedDay == day
                    val tieneHabitos = state.historial.any { it.day == day }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(4.dp)
                    ) {

                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .background(
                                    if (isSelected) Color(0xFF1E90FF) else Color.Transparent,
                                    CircleShape
                                )
                                .clickable {
                                    viewModel.onEvent(
                                        CalendarioEvent.OnDateSelected(day)
                                    )
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                day.toString(),
                                color = Color.White,
                                fontSize = 14.sp
                            )
                        }

                        if (tieneHabitos) {
                            Box(
                                modifier = Modifier
                                    .size(5.dp)
                                    .background(Color(0xFF00C853), CircleShape)
                            )
                        }
                    }
                }
            }
        }
    }

    // MODAL
    if (state.showDialog) {

        val dia = state.historial.find {
            it.day == state.selectedDay
        }

        AlertDialog(
            onDismissRequest = {
                viewModel.onEvent(CalendarioEvent.OnCloseDialog)
            },
            confirmButton = {},
            containerColor = Color(0xFF102A43),
            shape = RoundedCornerShape(20.dp),
            title = {
                Text("Día ${state.selectedDay}", color = Color.White)
            },
            text = {
                if (dia != null) {
                    Column {
                        dia.retos.forEach {
                            Text("✔ $it", color = Color(0xFF00E676))
                        }
                    }
                } else {
                    Text("Sin hábitos ese día", color = Color.Gray)
                }
            }
        )
    }
}

@Composable
fun HeaderPro(state: CalendarioState) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF102A43), RoundedCornerShape(20.dp))
            .padding(16.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Column {
                Text("Racha", color = Color.Gray, fontSize = 12.sp)
                Text("${state.racha} días", color = Color.White)
            }

            Column(horizontalAlignment = Alignment.End) {
                Text("Puntos", color = Color.Gray, fontSize = 12.sp)
                Text("${state.puntos}", color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            val dias = listOf("L","M","X","J","V","S","D")

            dias.forEachIndexed { index, dia ->

                val activo = state.diasSemanaActivos.contains(index + 1)

                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .background(
                            if (activo) Color(0xFF1E90FF) else Color(0xFF243B53),
                            CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(dia, color = Color.White, fontSize = 12.sp)
                }
            }
        }
    }
}