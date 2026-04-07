package com.polarsoft.polarpets.core.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FloatingMenu(
    currentRoute: String?,
    onNavigate: (String) -> Unit
) {
    // No mostrar el menú en la pantalla de Auth (Login/Register)
    if (currentRoute == NavRoutes.AUTH) return

    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 60.dp, end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedVisibility(
                visible = expanded,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut()
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    // Usamos el componente personalizado MenuItem definido abajo
                    MenuItem("🐾") {
                        onNavigate(NavRoutes.MASCOTA)
                        expanded = false
                    }
                    MenuItem("🛒") {
                        onNavigate(NavRoutes.TIENDA)
                        expanded = false
                    }
                    MenuItem("📅") {
                        onNavigate(NavRoutes.CALENDARIO)
                        expanded = false
                    }
                    MenuItem("⚙️") {
                        onNavigate(NavRoutes.CONFIGURACION)
                        expanded = false
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Botón principal del menú (el que expande/colapsa)
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(Color(0xFF396DC5), CircleShape)
                    .clickable { expanded = !expanded },
                contentAlignment = Alignment.Center
            ) {
                Text(if (expanded) "✕" else "⋯", color = Color.White, fontSize = 20.sp)
            }
        }
    }
}


@Composable
fun MenuItem(
    icon: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(bottom = 8.dp)
            .size(42.dp)
            .background(Color(0xFF396DC5).copy(alpha = 0.9f), CircleShape)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(text = icon, fontSize = 20.sp)
    }
}