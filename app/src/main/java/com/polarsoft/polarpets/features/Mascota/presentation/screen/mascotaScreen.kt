package com.polarsoft.polarpets.features.Mascota.presentation.screen
import androidx.compose.animation.core.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.Image
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.hilt.navigation.compose.hiltViewModel
import com.polarsoft.polarpets.features.Mascota.presentation.viewmodel.MascotaViewModel
import com.polarsoft.polarpets.features.Mascota.presentation.event.MascotaEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MascotaScreen(
    viewModel: MascotaViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()

    val progresoObjetivo = state.puntosActuales.toFloat() / state.puntosMaximos

    val progresoAnimado by animateFloatAsState(
        targetValue = progresoObjetivo,
        animationSpec = tween(1200, easing = FastOutSlowInEasing),
        label = "progress"
    )

    val scaffoldState = rememberBottomSheetScaffoldState()

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = 90.dp, // 🔥 siempre visible
        sheetContainerColor = Color(0xFF2C3E50),
        sheetShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        sheetContent = {
            BottomSheetContent()
        }
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color(0xFF6FA8DC),
                            Color(0xFF1E3C72)
                        )
                    )
                )
        ) {

            // 🔥 CONTENIDO CENTRADO REAL
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Header()

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = state.nombre,
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(20.dp))

                Image(
                    painter = painterResource(state.imagen),
                    contentDescription = null,
                    modifier = Modifier.size(260.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = {
                        viewModel.onEvent(MascotaEvent.OnEditarClick)
                    },
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF3A3A3A)
                    )
                ) {
                    Text("🎨 Editarme", fontSize = 12.sp)
                }

                Spacer(modifier = Modifier.height(20.dp))

                BarraProgreso(
                    progreso = progresoAnimado,
                    puntos = "${state.puntosActuales}/${state.puntosMaximos}",
                    nivel = "NV ${state.nivel}"
                )
            }
        }
    }
}

@Composable
fun Header() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Mascota",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.size(24.dp)) // menú externo
    }
}

@Composable
fun BarraProgreso(
    progreso: Float,
    puntos: String,
    nivel: String
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        Text(
            text = "Barra de Puntos",
            color = Color.White,
            fontSize = 12.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp)
                .clip(RoundedCornerShape(50))
                .background(Color(0xFF2E2E2E))
        ) {

            // 🔥 PROGRESO CON GRADIENTE PRO
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(progreso)
                    .clip(RoundedCornerShape(50))
                    .background(
                        Brush.horizontalGradient(
                            listOf(
                                Color(0xFF00F260), // verde neon
                                Color(0xFF0575E6)  // azul
                            )
                        )
                    )
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = puntos,
            color = Color.White,
            fontSize = 12.sp
        )

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = nivel,
            color = Color(0xFFFFD700),
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BottomSheetContent() {

    val pagerState = rememberPagerState(pageCount = { 3 })

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .navigationBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(8.dp))

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.height(220.dp)
        ) { page ->

            when (page) {
                0 -> HabitosPage()
                1 -> RetosPage()
                2 -> CrearHabitoPage()
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row {
            repeat(3) { index ->
                val color =
                    if (pagerState.currentPage == index) Color.White else Color.Gray

                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .size(6.dp)
                        .clip(CircleShape)
                        .background(color)
                )
            }
        }
    }
}
@Composable
fun HabitosPage() {

    var h1 by remember { mutableStateOf(false) }
    var h2 by remember { mutableStateOf(false) }
    var h3 by remember { mutableStateOf(false) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        Text(
            "Hábitos diarios",
            color = Color(0xFF00FF88),
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        ItemHabito("+2", "Ir al Gym", h1) { h1 = it }
        ItemHabito("+1", "Hacer Tarea", h2) { h2 = it }
        ItemHabito("+5", "Jugar con tu perro", h3) { h3 = it }
    }
}

@Composable
fun RetosPage() {

    var r1 by remember { mutableStateOf(false) }
    var r2 by remember { mutableStateOf(false) }
    var r3 by remember { mutableStateOf(false) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        Text(
            "RETOS DIARIOS",
            color = Color(0xFFFFC107),
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(12.dp))
        ItemHabito("+10", "Jugar Halo", r1) { r1 = it }
        ItemHabito("+50", "Ir al psicólogo", r2) { r2 = it }
        ItemHabito("+100", "Escuchar música de MJ", r3) { r3 = it }

    }
}

@Composable
fun CrearHabitoPage() {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text("No tienes ningún reto crea mas!", color = Color.White)

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .background(Color(0xFF4CAF50)),
            contentAlignment = Alignment.Center
        ) {
            Text("+", fontSize = 30.sp, color = Color.White)
        }
    }
}

@Composable
fun ItemHabito(
    puntos: String,
    texto: String,
    checked: Boolean = false,
    onCheckedChange: (Boolean) -> Unit = {}
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            text = puntos,
            color = Color.White,
            fontSize = 10.sp
        )

        Text(
            text = texto,
            color = Color.White,
            fontSize = 10.sp,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center,
            maxLines = 1
        )

        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(
                checkedColor = Color(0xFF4CAF50),
                uncheckedColor = Color.Gray,
                checkmarkColor = Color.White
            )
        )
    }
}