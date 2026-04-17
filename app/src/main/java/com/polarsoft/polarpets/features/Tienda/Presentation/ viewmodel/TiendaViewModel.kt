package com.polarsoft.polarpets.FeaturesTiendaPresentatio.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polarsoft.polarpets.core.session.SessionManager
import com.polarsoft.polarpets.features.Tienda.domain.model.Producto
import com.polarsoft.polarpets.features.Tienda.domain.repository.TiendaRepository
import com.polarsoft.polarpets.features.Tienda.Presentation.State.TiendaState
import com.polarsoft.polarpets.features.Tienda.Presentation.event.TiendaEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject

@HiltViewModel
class TiendaViewModel @Inject constructor(
    private val tiendaRepository: TiendaRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _state = MutableStateFlow(TiendaState())
    val state: StateFlow<TiendaState> = _state

    // Tu Access Token de MercadoPago sandbox
    private val MP_ACCESS_TOKEN = "APP_USR-188513502103507-041702-163087902f3770dc32cc9a4517cdae03-3343204132"

    init {
        cargarProductos()
    }

    fun onEvent(event: TiendaEvent) {
        when (event) {
            is TiendaEvent.OnComprarClick -> iniciarCompra(event.producto)
            is TiendaEvent.OnPagoConfirmado -> confirmarPago(event.paymentId)
            is TiendaEvent.OnPagoCancelado -> {
                _state.update { it.copy(checkoutUrl = null, productoSeleccionado = null) }
            }
            is TiendaEvent.OnDismissError -> {
                _state.update { it.copy(error = null) }
            }
        }
    }

    private fun cargarProductos() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            
            val result = tiendaRepository.getProductos()
            result.onSuccess { productos ->
                _state.update { 
                    it.copy(
                        isLoading = false,
                        productos = productos,
                        error = null
                    ) 
                }
            }.onFailure { e ->
                _state.update { 
                    it.copy(
                        isLoading = false,
                        error = null
                    ) 
                }
            }
        }
    }

    private fun iniciarCompra(producto: Producto) {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                _state.update { it.copy(isLoading = true, productoSeleccionado = producto) }
            }

            val userId = sessionManager.idUsuario.first()
            Log.d("TiendaVM", "idUsuario = $userId")

            if (userId == null || userId == 0) {
                withContext(Dispatchers.Main) {
                    _state.update { it.copy(error = "Error: Sesión no válida. Por favor, re-inicia sesión.", isLoading = false) }
                }
                return@launch
            }

            try {
                val url = URL("https://api.mercadopago.com/checkout/preferences")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "POST"
                connection.setRequestProperty("Authorization", "Bearer $MP_ACCESS_TOKEN")
                connection.setRequestProperty("Content-Type", "application/json")
                connection.doOutput = true

                val body = JSONObject().apply {
                    put("items", JSONArray().apply {
                        put(JSONObject().apply {
                            put("title", producto.nombre)
                            put("quantity", 1)
                            put("unit_price", producto.precio)
                            put("currency_id", "MXN")
                        })
                    })
                    put("back_urls", JSONObject().apply {
                        put("success", "polarpets://pago/success")
                        put("failure", "polarpets://pago/failure")
                        put("pending", "polarpets://pago/pending")
                    })
                    put("auto_return", "approved")
                }

                connection.outputStream.write(body.toString().toByteArray())

                val responseCode = connection.responseCode
                if (responseCode == 201) {
                    val response = connection.inputStream.bufferedReader().readText()
                    val json = JSONObject(response)
                    val checkoutUrl = json.getString("init_point")
                    withContext(Dispatchers.Main) {
                        _state.update { it.copy(checkoutUrl = checkoutUrl, isLoading = false) }
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        _state.update { it.copy(error = "Error al crear preferencia de pago", isLoading = false) }
                    }
                }

            } catch (e: Exception) {
                Log.e("TiendaVM", "Error en iniciarCompra: ${e.message}", e)
                withContext(Dispatchers.Main) {
                    _state.update { it.copy(isLoading = false, error = e.message ?: "Error desconocido") }
                }
            }
        }
    }

    private fun confirmarPago(paymentId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val idUsuario = sessionManager.idUsuario.first() ?: return@launch
            val token = sessionManager.token.first() ?: return@launch
            val producto = _state.value.productoSeleccionado ?: return@launch

            try {
                // 1 — Registrar pago en tu API
                val urlPago = URL("http://52.6.69.214:8000/pagos/")
                val connPago = urlPago.openConnection() as HttpURLConnection
                connPago.requestMethod = "POST"
                connPago.setRequestProperty("Content-Type", "application/json")
                connPago.setRequestProperty("Authorization", "Bearer $token")
                connPago.doOutput = true

                val bodyPago = JSONObject().apply {
                    put("id_usuario", idUsuario)
                    put("monto", producto.precio)
                    put("metodo_pago", "mercadopago")
                    put("estado", "completado")
                    put("fecha", java.time.Instant.now().toString())
                    put("referencia_externa", paymentId)
                }
                connPago.outputStream.write(bodyPago.toString().toByteArray())

                val responsePago = JSONObject(connPago.inputStream.bufferedReader().readText())
                val idPago = responsePago.getInt("id_pago")

                // 2 — Registrar compra
                val urlCompra = URL("http://52.6.69.214:8000/compras/")
                val connCompra = urlCompra.openConnection() as HttpURLConnection
                connCompra.requestMethod = "POST"
                connCompra.setRequestProperty("Content-Type", "application/json")
                connCompra.setRequestProperty("Authorization", "Bearer $token")
                connCompra.doOutput = true

                val bodyCompra = JSONObject().apply {
                    put("id_usuario", idUsuario)
                    put("id_producto", producto.idProducto)
                    put("id_pago", idPago)
                    put("fecha", java.time.Instant.now().toString())
                }
                connCompra.outputStream.write(bodyCompra.toString().toByteArray())
                connCompra.inputStream.bufferedReader().readText()

                // 3 — Agregar al inventario
                val urlInv = URL("http://52.6.69.214:8000/inventario/")
                val connInv = urlInv.openConnection() as HttpURLConnection
                connInv.requestMethod = "POST"
                connInv.setRequestProperty("Content-Type", "application/json")
                connInv.setRequestProperty("Authorization", "Bearer $token")
                connInv.doOutput = true

                val bodyInv = JSONObject().apply {
                    put("id_usuario", idUsuario)
                    put("id_producto", producto.idProducto)
                }
                connInv.outputStream.write(bodyInv.toString().toByteArray())
                connInv.inputStream.bufferedReader().readText()

                withContext(Dispatchers.Main) {
                    _state.update {
                        it.copy(
                            checkoutUrl = null,
                            productoSeleccionado = null,
                            compraExitosa = true,
                            mensajeCompra = "¡Compra exitosa! ${producto.nombre} agregado a tu inventario"
                        )
                    }
                    cargarProductos()
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _state.update { it.copy(error = "Error al confirmar compra: ${e.message}") }
                }
            }
        }
    }
}