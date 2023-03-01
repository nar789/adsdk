package com.topping.adsdk

import android.os.Bundle
import android.widget.Button
import com.topping.adsdk.dto.UserInfo
import com.topping.adsdk.view.AdSdkActivity

class TestMainActivity : AdSdkActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test_activity_main)

        val btnCreateAd = findViewById<Button>(R.id.btn_create_ad)
        btnCreateAd.setOnClickListener { sdkCreateSample() }

        val btnOnAd = findViewById<Button>(R.id.btn_on_ad)
        btnOnAd.setOnClickListener { sdkOnSample() }
    }

    override fun getAppKey(): String {
        return "testKey1234"
    }

    override fun getUserInfo(): UserInfo {
        return UserInfo("male", "22")
    }

    override fun getBaseUrl(): String {
        return "http://14.47.39.138:3311/"
    }



    private fun sdkOnSample() {
        val sampleUrl = "http://14.47.39.138:3311/on"
        val datetime = System.currentTimeMillis()
        sdk.on(sampleUrl, "3", datetime, getUserInfo())
    }


    private fun sdkCreateSample() {
        sdk.create("POPUP", getUserInfo())
    }
}