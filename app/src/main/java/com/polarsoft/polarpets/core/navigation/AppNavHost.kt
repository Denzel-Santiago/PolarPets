package com.polarsoft.polarpets.core.navigation

import androidx.compose.runtime.*
import androidx.navigation.compose.*
import com.polarsoft.polarpets.features.Calendario.Presentation.Screen.CalendarioScreen
import com.polarsoft.polarpets.features.Login.presentation.screen.LoginScreen
import com.polarsoft.polarpets.features.Mascota.presentation.screen.MascotaScreen
import com.polarsoft.polarpets.features.Tienda.Presentation.Screen.TiendaScreen
import com.polarsoft.polarpets.features.configuracion.presentation.screen.configuracionScreen

@Composable
fun AppNavHost() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavRoutes.AUTH
    ) {

        // 🔐 AUTH (Login + Register interno)
        composable(NavRoutes.AUTH) {
            LoginScreen(
                onNavigateToTienda = {
                    navController.navigate(NavRoutes.TIENDA) {
                        popUpTo(NavRoutes.AUTH) { inclusive = true }
                    }
                }
            )
        }

        // 🐾 Pantallas principales
        composable(NavRoutes.MASCOTA) {
            MascotaScreen()
        }

        composable(NavRoutes.TIENDA) {
            TiendaScreen()
        }

        composable(NavRoutes.CALENDARIO) {
            CalendarioScreen()
        }

        composable(NavRoutes.CONFIGURACION) {
            configuracionScreen()
        }
    }


    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    FloatingMenu(
        currentRoute = currentRoute,
        onNavigate = { route ->
            navController.navigate(route)
        }
    )
}
