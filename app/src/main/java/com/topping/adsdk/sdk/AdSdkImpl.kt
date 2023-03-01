package com.topping.adsdk.sdk

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.topping.adsdk.dto.RequestCreateData
import com.topping.adsdk.dto.RequestOnData
import com.topping.adsdk.dto.ResponseCreateData
import com.topping.adsdk.dto.UserInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*


class AdSdkImpl(val baseUrl: String, val adLoader: AdSdk.Loadable) : AdSdk {
    private val TAG = "TOPPING/AdSdkImpl"

    var token: String = ""
    private val gson: Gson = GsonBuilder().setLenient().create()
    private val retrofit: Retrofit = Retrofit.Builder().baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create(gson)).build()
    private val service: AdSdkService = retrofit.create(AdSdkService::class.java)

    override fun init(key: String) {
        Log.d(TAG, "/init key = $key")
        service.postInit(key).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    val result: String? = response.body()
                    token = result ?: ""
                    Log.d(TAG, "/init token $result")
                } else {
                    Log.d(TAG, "onResponse fail")
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d(TAG, "/init onFailure, " + t.message)
            }

        })
    }

    override fun create(type: String, userInfo: UserInfo) {
        Log.d(TAG, "/create token $token")
        if (token.isEmpty()) {
            Log.d(TAG, "/create, token is empty!")
            return
        }
        val data = RequestCreateData(token, type, userInfo)
        service.postCreate(data).enqueue(object : Callback<List<ResponseCreateData>> {
            override fun onResponse(
                call: Call<List<ResponseCreateData>>,
                response: Response<List<ResponseCreateData>>
            ) {
                if (response.isSuccessful) {
                    val result: List<ResponseCreateData>? = response.body()
                    Log.d(TAG, "/create result $result")

                    val list: List<ResponseCreateData> = result ?: ArrayList<ResponseCreateData>()
                    val random = Random()
                    val idx = random.nextInt(list.size)
                    adLoader.load(list[idx])

                } else {
                    Log.d(TAG, "/create onResponse fail")
                }
            }

            override fun onFailure(call: Call<List<ResponseCreateData>>, t: Throwable) {
                Log.d(TAG, "/create onFailure, " + t.message)
            }

        })
        //1. post token, type, userinfo
        //2. get list
        //3. item(adId, img url, href, traceUrl)
        //4. create ad
        //5. ad click listener - > move href and backtrace( traceUrl, adId, now())
    }

    override fun on(url: String, adId: String, datetime: Long, userInfo: UserInfo) {
        if (token.isEmpty()) {
            Log.d(TAG, "trace(), token is empty!")
            return
        }
        val data = RequestOnData(token, adId, datetime, userInfo)
        service.postOn(data).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    val result: String? = response.body()
                    Log.d(TAG, "/on result $result")
                } else {
                    Log.d(TAG, "/on onResponse fail")
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d(TAG, "/on onFailure, " + t.message)
            }
        });
        //1.post on url / token, adId, datetime, userinfo
    }
}