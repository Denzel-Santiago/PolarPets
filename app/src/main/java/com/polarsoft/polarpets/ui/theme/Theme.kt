package com.polarsoft.polarpets.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// 🌞 Colores modo claro (basado en tu diseño)
private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF5B9CFF),       // azul principal (botones)
    secondary = Color(0xFF8EC5FC),     // azul claro
    background = Color(0xFFEAF4FF),    // fondo cielo
    surface = Color(0xFFFFFFFF),       // tarjetas
    onPrimary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F)
)

// 🌙 Colores modo oscuro (adaptados a tu estilo)
private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF1E3A5F),       // azul oscuro
    secondary = Color(0xFF4A6FA5),
    background = Color(0xFF0D1B2A),    // fondo oscuro elegante
    surface = Color(0xFF1B263B),       // tarjetas oscuras
    onPrimary = Color.White,
    onBackground = Color(0xFFE0E1DD),
    onSurface = Color(0xFFE0E1DD)
)

@Composable
fun PolarPetsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}