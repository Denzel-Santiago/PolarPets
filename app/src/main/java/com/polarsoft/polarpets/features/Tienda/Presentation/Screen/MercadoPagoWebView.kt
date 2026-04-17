package com.polarsoft.polarpets.features.Tienda.Presentation.Screen

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun MercadoPagoWebView(
    url: String,
    onSuccess: (String) -> Unit,
    onCancel: () -> Unit
) {
    AndroidView(factory = { context ->
        WebView(context).apply {
            settings.javaScriptEnabled = true
            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    urlString: String?
                ): Boolean {
                    urlString?.let {
                        when {
                            it.contains("polarpets://pago/success") -> {
                                val paymentId = it.substringAfter("payment_id=")
                                    .substringBefore("&")
                                onSuccess(paymentId)
                            }
                            it.contains("polarpets://pago/failure") ||
                            it.contains("polarpets://pago/pending") -> {
                                onCancel()
                            }
                        }
                    }
                    return false
                }
            }
            loadUrl(url)
        }
    })
}