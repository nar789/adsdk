package com.topping.adsdk.dto

import com.google.gson.annotations.SerializedName

data class ResponseCreateData(

    @SerializedName("adId")
    val adId: String,

    @SerializedName("imgUrl")
    val imgUrl: String,

    @SerializedName("href")
    val href: String,

    @SerializedName("traceUrl")
    val traceUrl: String

)