package com.jumia.myapplication.infrastructure.dto

import com.google.gson.annotations.SerializedName


data class AppConfigurationResponse (

    @SerializedName("success") var success : Boolean,
    @SerializedName("session") var session : JumSession,
    @SerializedName("metadata") var metadata : JumConfiguration

)

data class JumConfiguration (
    @SerializedName("currency") val currency : JumCurrency,
    @SerializedName("languages") val languages : List<JumLanguages>,
    @SerializedName("support") val support : JumSupport
)

data class JumSession (
    @SerializedName("id") val id : String,
    @SerializedName("expire") val expire : String,
    @SerializedName("YII_CSRF_TOKEN") val YIICSRFTOKEN : String
)

data class JumCurrency (
    @SerializedName("iso") val iso : String,
    @SerializedName("currency_symbol") val currencySymbol : String,
    @SerializedName("position") val position : Int,
    @SerializedName("decimals") val decimals : Int,
    @SerializedName("thousands_sep") val thousandsSep : String,
    @SerializedName("decimals_sep") val decimalsSep : String
)

data class JumLanguages (
    @SerializedName("code") val code : String,
    @SerializedName("name") val name : String,
    @SerializedName("default") val default : Boolean
)

data class JumSupport (
    @SerializedName("phone_number") val phoneNumber : String,
    @SerializedName("call_to_order_enabled") val callToOrderEnabled : Boolean,
    @SerializedName("cs_email") val csEmail : String
)