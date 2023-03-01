package com.topping.adsdk.dto

import com.google.gson.annotations.SerializedName

data class UserInfo(
    @SerializedName("sex")
    val sex: String,

    @SerializedName("age")
    val age: String
)