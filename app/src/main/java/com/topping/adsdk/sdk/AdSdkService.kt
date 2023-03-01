package com.topping.adsdk.sdk

import com.topping.adsdk.dto.RequestCreateData
import com.topping.adsdk.dto.RequestOnData
import com.topping.adsdk.dto.ResponseCreateData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AdSdkService {

    @FormUrlEncoded
    @POST("/init")
    fun postInit(@Field("key") key: String): Call<String>

    @POST("/create")
    fun postCreate(@Body data:RequestCreateData): Call<List<ResponseCreateData>>

    @POST("/on")
    fun postOn(@Body data:RequestOnData): Call<String>

}