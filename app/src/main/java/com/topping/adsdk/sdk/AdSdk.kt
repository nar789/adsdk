package com.topping.adsdk.sdk

import com.topping.adsdk.dto.ResponseCreateData
import com.topping.adsdk.dto.UserInfo

interface AdSdk {

    fun init(key: String)
    fun create(type: String, userInfo: UserInfo)
    fun on(url: String, adId: String, datetime: Long, userInfo: UserInfo)

    interface Loadable {
        fun load(data: ResponseCreateData)
    }

}