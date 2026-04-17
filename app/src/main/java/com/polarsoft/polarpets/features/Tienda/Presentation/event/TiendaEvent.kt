package com.polarsoft.polarpets.features.Tienda.Presentation.event

import com.polarsoft.polarpets.features.Tienda.domain.model.Producto

sealed class TiendaEvent {
    data class OnComprarClick(val producto: Producto) : TiendaEvent()
    data class OnPagoConfirmado(val paymentId: String) : TiendaEvent()
    object OnPagoCancelado : TiendaEvent()
    object OnDismissError : TiendaEvent()
}
