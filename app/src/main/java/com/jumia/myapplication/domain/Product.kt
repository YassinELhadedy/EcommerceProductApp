package com.jumia.myapplication.domain

import com.jumia.myapplication.infrastructure.dto.Rating
import com.jumia.myapplication.infrastructure.dto.SellerEntity
import com.jumia.myapplication.infrastructure.dto.Summary

data class Product(
    val sort: String?,
    val sku: String,
    val name: String,
    val brand: String,
    val maxSavingPercentage: Int,
    val price: Int,
    val special_price: Int,
    val image: String?,
    val ratingAverage: Int,
    val rating: Rating?,
    val imageList: List<String>?,
    val summary: Summary?,
    val sellerEntity: SellerEntity?
)

