package com.polarsoft.polarpets

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.polarsoft.polarpets.core.navigation.MainContainer
import com.polarsoft.polarpets.ui.theme.PolarPetsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            PolarPetsTheme {
                MainContainer()
            }
        }
    }
}
