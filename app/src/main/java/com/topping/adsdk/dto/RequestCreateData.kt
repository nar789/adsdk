package com.topping.adsdk.dto

import com.google.gson.annotations.SerializedName

data class RequestCreateData(

    @SerializedName("token")
    val token: String,

    @SerializedName("type")
    val type: String,

    @SerializedName("userInfo")
    val userInfo: UserInfo
)