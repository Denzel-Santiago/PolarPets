package com.polarsoft.polarpets.features.Tienda.Presentation.State

import com.polarsoft.polarpets.features.Tienda.domain.model.Producto

data class TiendaState(
    val productos: List<Producto> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val checkoutUrl: String? = null,        // URL de MercadoPago para abrir WebView
    val productoSeleccionado: Producto? = null,
    val compraExitosa: Boolean = false,
    val mensajeCompra: String? = null
)
