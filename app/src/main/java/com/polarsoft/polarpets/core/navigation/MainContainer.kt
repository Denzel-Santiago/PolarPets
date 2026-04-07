package com.polarsoft.polarpets.core.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MainContainer() {
    Box(modifier = Modifier.fillMaxSize()) {
        AppNavHost()
    }
}