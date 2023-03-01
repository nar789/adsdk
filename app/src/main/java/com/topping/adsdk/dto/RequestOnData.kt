package com.topping.adsdk.dto

import com.google.gson.annotations.SerializedName

data class RequestOnData(

    @SerializedName("token")
    val token: String,

    @SerializedName("adId")
    val adId: String,

    @SerializedName("datetime")
    val datetime: Long,

    @SerializedName("userInfo")
    val userInfo: UserInfo
)