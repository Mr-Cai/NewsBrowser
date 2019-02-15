package news.browser.module.web

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_web.*
import news.browser.R

class WebActivity : AppCompatActivity() {
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(onSavedInstanceState: Bundle?) {
        super.onCreate(onSavedInstanceState)
        setContentView(R.layout.activity_web)
        webView.settings.apply {
            javaScriptEnabled = true
            setSupportZoom(true)
            layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
            setAppCacheEnabled(true)
        }
        webView.apply {
            loadUrl(intent.getStringExtra("url"))
            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView,
                                                      request: WebResourceRequest): Boolean {
                    view.loadUrl(request.url.toString())
                    return super.shouldOverrideUrlLoading(view, request)
                }

                override fun onPageFinished(view: WebView, url: String) {
                    super.onPageFinished(view, url)
                    loadCircle.visibility = View.GONE
                }
            }
        }
    }
}
