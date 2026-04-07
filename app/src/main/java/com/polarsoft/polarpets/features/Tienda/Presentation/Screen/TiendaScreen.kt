package com.polarsoft.polarpets.features.Tienda.Presentation.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.polarsoft.polarpets.features.Tienda.Presentation.event.TiendaEvent
import com.polarsoft.polarpets.FeaturesTiendaPresentatio.viewmodel.TiendaViewModel

@Composable
fun TiendaScreen(
    modifier: Modifier = Modifier,
    viewModel: TiendaViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFF8DB6E9),
                        Color(0xFF1E3A5F)
                    )
                )
            )
            .padding(16.dp)
    ) {

        Column {

            // 🔹 HEADER
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Tienda", color = Color.White, fontSize = 18.sp)

                Row {
                    repeat(3) {
                        Dot()
                        Spacer(modifier = Modifier.width(4.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // 🔹 MASCOTAS
            Title("MASCOTA")

            Spacer(modifier = Modifier.height(12.dp))

            StoreCard(
                imageRes = state.mascota.first(),
                onClick = {
                    viewModel.onEvent(TiendaEvent.OnMascotaClick(0))
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // 🔹 TRAJES
            Title("Trajes de mascotas")

            Spacer(modifier = Modifier.height(12.dp))

            StoreCard(
                imageRes = state.trajes.first(),
                onClick = {
                    viewModel.onEvent(TiendaEvent.OnTrajeClick(0))
                }
            )
        }
    }
}

@Composable
fun Title(text: String) {
    Text(
        text = text,
        color = Color.White,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center
    )
}

@Composable
fun Dot() {
    Box(
        modifier = Modifier
            .size(6.dp)
            .background(Color.White, CircleShape)
    )
}

@Composable
fun StoreCard(
    imageRes: Int,
    onClick: () -> Unit
) {

    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .clickable { onClick() },
            shape = RoundedCornerShape(20.dp),
            border = BorderStroke(1.dp, Color.White.copy(alpha = 0.6f)),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF214E80)
            )
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = null,
                    modifier = Modifier.size(130.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row {
            repeat(3) {
                Dot()
                Spacer(modifier = Modifier.width(4.dp))
            }
        }
    }
}