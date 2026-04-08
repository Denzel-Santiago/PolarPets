package com.polarsoft.polarpets.features.Tienda.Presentation.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.remember
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import com.polarsoft.polarpets.features.Tienda.Presentation.State.Traje


// 🔄 INTERCALAR
fun intercalarTrajes(trajes: List<Traje>): List<Traje> {
    val normales = trajes.filter { !it.esPremium }
    val premium = trajes.filter { it.esPremium }

    val resultado = mutableListOf<Traje>()
    val max = maxOf(normales.size, premium.size)

    for (i in 0 until max) {
        if (i < normales.size) resultado.add(normales[i])
        if (i < premium.size) resultado.add(premium[i])
    }

    return resultado
}

@Composable
fun TiendaScreen(
    modifier: Modifier = Modifier,
    viewModel: TiendaViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()

    val trajesIntercalados = remember(state.trajes) {
        intercalarTrajes(state.trajes)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFF8DB6E9), Color(0xFF1E3A5F))
                )
            )
            .padding(16.dp)
    ) {

        // HEADER
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

        // 🐾 MASCOTAS
        Title("MASCOTA")

        Spacer(modifier = Modifier.height(12.dp))

        LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            items(state.mascota.size) { index ->
                StoreCard(
                    imageRes = state.mascota[index],
                    onClick = {
                        viewModel.onEvent(TiendaEvent.OnMascotaClick(index))
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 👕 TRAJES
        Title("Trajes de mascotas")

        Spacer(modifier = Modifier.height(12.dp))

        LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            items(trajesIntercalados) { traje ->

                if (traje.esPremium && !traje.comprado) {
                    PremiumCard()
                } else {
                    TrajeCard(
                        traje = traje,
                        onComprar = {
                            viewModel.onEvent(
                                TiendaEvent.OnComprarClick(traje.id)
                            )
                        }
                    )
                }
            }
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
fun StoreCard(imageRes: Int, onClick: () -> Unit) {

    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        Card(
            modifier = Modifier
                .width(180.dp)
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


    }
}

@Composable
fun TrajeCard(traje: Traje, onComprar: () -> Unit) {

    Card(
        modifier = Modifier.width(160.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF214E80)
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(id = traje.Image),
                contentDescription = null,
                modifier = Modifier.size(100.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(traje.nombre, color = Color.White)

            Spacer(modifier = Modifier.height(8.dp))

            if (traje.comprado) {
                Text("Comprado ✅", color = Color.Green)
            } else {
                Box(
                    modifier = Modifier
                        .background(Color(0xFF396DC5), RoundedCornerShape(12.dp))
                        .clickable { onComprar() }
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text("Comprar", color = Color.White)
                }
            }
        }
    }
}

@Composable
fun PremiumCard() {

    Card(
        modifier = Modifier.width(160.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF2F3E4E)
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                "Desbloquea siendo Premium",
                color = Color.White,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(12.dp))

            Box(
                modifier = Modifier
                    .background(Color(0xFFFF9800), RoundedCornerShape(20.dp))
                    .padding(horizontal = 20.dp, vertical = 8.dp)
            ) {
                Text("Ser Premium", color = Color.White)
            }
        }
    }
}