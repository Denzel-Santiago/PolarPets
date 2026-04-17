package com.polarsoft.polarpets.features.Tienda.Presentation.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.Image
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.polarsoft.polarpets.R
import com.polarsoft.polarpets.features.Tienda.Presentation.event.TiendaEvent
import com.polarsoft.polarpets.FeaturesTiendaPresentatio.viewmodel.TiendaViewModel
import com.polarsoft.polarpets.features.Tienda.domain.model.Producto

@Composable
fun TiendaScreen(
    modifier: Modifier = Modifier,
    viewModel: TiendaViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    // WebView de MercadoPago
    if (state.checkoutUrl != null) {
        MercadoPagoWebView(
            url = state.checkoutUrl!!,
            onSuccess = { paymentId ->
                viewModel.onEvent(TiendaEvent.OnPagoConfirmado(paymentId))
            },
            onCancel = {
                viewModel.onEvent(TiendaEvent.OnPagoCancelado)
            }
        )
        return
    }

    // Separar mascotas (idTipoMascota != null y precio == 0) y trajes (resto)
    val mascotas = state.productos.filter { it.idTipoMascota != null && it.precio == 0.0 }
    val trajes = state.productos.filter { it.precio > 0 }

    // Intercalar: primero los "normales" (precio bajo) luego "premium" (precio alto)
    val trajesOrdenados = trajes.sortedBy { it.precio }

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
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .background(Color.White, CircleShape)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Loading o errores
        if (state.isLoading) {
            Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Color.White)
            }
        }
        state.error?.let {
            Text(it, color = Color(0xFFFF6B6B), modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
        }
        state.mensajeCompra?.let {
            Text(it, color = Color(0xFF2ECC71), modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
        }

        Spacer(modifier = Modifier.height(8.dp))

        // 🐾 MASCOTAS — si hay en API las muestra, si no muestra las hardcodeadas
        TiendaTitle("MASCOTA")
        Spacer(modifier = Modifier.height(12.dp))

        LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            if (mascotas.isEmpty()) {
                // Mascotas hardcodeadas como antes
                item {
                    MascotaCard(imageRes = R.drawable.foquita, onClick = {})
                }
                item {
                    MascotaCard(imageRes = R.drawable.pinguino, onClick = {})
                }
            } else {
                items(mascotas) { mascota ->
                    MascotaCardApi(nombre = mascota.nombre, onClick = {})
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 👕 TRAJES
        TiendaTitle("Trajes de mascotas")
        Spacer(modifier = Modifier.height(12.dp))

        LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            items(trajesOrdenados) { producto ->
                val esPremium = producto.precio >= 300.0
                if (esPremium) {
                    PremiumProductoCard(producto = producto, onComprar = {
                        viewModel.onEvent(TiendaEvent.OnComprarClick(producto))
                    })
                } else {
                    TrajeProductoCard(producto = producto, onComprar = {
                        viewModel.onEvent(TiendaEvent.OnComprarClick(producto))
                    })
                }
            }
        }
    }
}

@Composable
fun TiendaTitle(text: String) {
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
fun MascotaCard(imageRes: Int, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .width(180.dp)
            .height(220.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.6f)),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF214E80))
    ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                modifier = Modifier.size(130.dp)
            )
        }
    }
}

@Composable
fun MascotaCardApi(nombre: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .width(180.dp)
            .height(220.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.6f)),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF214E80))
    ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            Text(nombre, color = Color.White, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun TrajeProductoCard(producto: Producto, onComprar: () -> Unit) {
    Card(
        modifier = Modifier.width(160.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF214E80))
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(producto.nombre, color = Color.White, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(4.dp))
            Text("$${producto.precio} MXN", color = Color(0xFFFFD700), fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
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

@Composable
fun PremiumProductoCard(producto: Producto, onComprar: () -> Unit) {
    Card(
        modifier = Modifier.width(160.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2F3E4E))
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(producto.nombre, color = Color.White, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(4.dp))
            Text("$${producto.precio} MXN", color = Color(0xFFFFD700), fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .background(Color(0xFFFF9800), RoundedCornerShape(20.dp))
                    .clickable { onComprar() }
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text("Comprar Premium", color = Color.White)
            }
        }
    }
}