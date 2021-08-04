package com.jumia.myapplication.infrastructure

import com.google.gson.Gson

data class AppResponse(
        var success: Boolean = false,
        var metadata: Any? = null

) {
    fun getResult(): String {
        return Gson().toJson(metadata)
    }
}