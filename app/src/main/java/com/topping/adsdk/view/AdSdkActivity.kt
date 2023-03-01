package com.topping.adsdk.view

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Point
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.topping.adsdk.R
import com.topping.adsdk.dto.ResponseCreateData
import com.topping.adsdk.dto.UserInfo
import com.topping.adsdk.sdk.AdSdk
import com.topping.adsdk.sdk.AdSdkImpl

open class AdSdkActivity : Activity() {
    private val TAG = "TOPPING/AdSdkActivity"
    private lateinit var content: LinearLayout
    private lateinit var webView: WebView
    private lateinit var imageView: ImageView
    lateinit var sdk: AdSdk

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.setContentView(R.layout.activity_adsdk)
        content = findViewById(R.id.content)
        webView = findViewById(R.id.ad_sdk_webView)
        imageView = findViewById(R.id.ad_sdk_imageView)

        initImageView()
        initWebView()
        initSdk()
    }

    open fun getBaseUrl(): String {
        return ""
    }

    open fun getUserInfo(): UserInfo {
        return UserInfo("female", "22")
    }

    private fun initSdk() {
        if (getBaseUrl().isEmpty()) {
            Log.e(TAG, "error : base url is empty")
            return;
        }
        sdk = AdSdkImpl(getBaseUrl(), object : AdSdk.Loadable {
            override fun load(data: ResponseCreateData) {
                loadAdImage(data)
            }
        })
        sdk.init(getAppKey())
    }

    open fun getAppKey(): String {
        return "DefaultKeyPleaseOverrideGetKeyMethod"
    }

    private fun loadAdImage(data: ResponseCreateData) {
        Glide.with(this)
            .load(data.imgUrl)
            .into(imageView)
        imageView.setOnClickListener {
            Log.d(TAG, "image view clicked  ${data.href}")
            webView.clearHistory()
            webView.loadUrl(data.href)
            imageView.visibility = View.GONE
            webView.visibility = View.VISIBLE
            val datetime = System.currentTimeMillis()
            sdk.on(data.traceUrl, data.adId, datetime, getUserInfo())
        }
        imageView.visibility = View.VISIBLE
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {
        webView.webChromeClient = WebChromeClient()
        webView.webViewClient = WebViewClient()
        webView.settings.javaScriptEnabled = true
    }

    private fun initImageView() {
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        imageView.layoutParams.width = (size.x * 0.9).toInt()
        imageView.layoutParams.height = (size.y * 0.9).toInt()
    }

    override fun setContentView(layoutResID: Int) {
        var layoutInflater = LayoutInflater.from(this)
        if (::content.isInitialized) {
            layoutInflater.inflate(layoutResID, content)
        }
    }

    override fun onBackPressed() {
        if (webView.isVisible) {
            webView.visibility = View.GONE
            webView.loadUrl("")
        } else if (imageView.isVisible) {
            imageView.visibility = View.GONE
        } else {
            super.onBackPressed()
        }
    }
}