package com.jumia.myapplication.domain

data class Product(
    val sku: String,
    val name: String,
    val rate:Double,
    val likes:Int,
    val shares:Int,
    val image: String,
    val deliveryFee: Double,
    val isSpecialOffer:Boolean,
    val isPromoOffer:Boolean,
    val store:Store
)

