package com.jumia.myapplication.infrastructure.dto

import com.google.gson.annotations.SerializedName
import com.jumia.myapplication.domain.Product


data class JumProductMetadata(
    @SerializedName("sort") val sort: String,
    @SerializedName("total_products") val totalProducts: Int,
    @SerializedName("title") val title: String,
    @SerializedName("results") val products: List<JumProduct>
) {
    fun toProducts(): List<Product> = products.map {
        it.toProduct().copy(sort = sort)
    }
}


class JumProduct(
    @SerializedName("sku") val sku: String,
    @SerializedName("name") val name: String,
    @SerializedName("brand") val brand: String,
    @SerializedName("max_saving_percentage") val maxSavingPercentage: Int,
    @SerializedName("price") val price: Int,
    @SerializedName("special_price") val special_price: Int,
    @SerializedName("image") val image: String?,
    @SerializedName("rating_average") val ratingAverage: Int,
    @SerializedName("rating") val rating : Rating?,
    @SerializedName("image_list") val imageList: List<String>?,
    @SerializedName("summary") val summary: Summary?,
    @SerializedName("seller_entity") val sellerEntity: SellerEntity?

) {
    companion object {
        fun Product.toJumProduct(): JumProduct = JumProduct(
            sku, name, brand, maxSavingPercentage, price, special_price, image, ratingAverage,rating, imageList, summary, sellerEntity
        )
    }

    fun toProduct(): Product = Product(
        null, sku, name, brand, maxSavingPercentage, price, special_price, image, ratingAverage,rating, imageList, summary, sellerEntity
    )
}

data class Rating (

    @SerializedName("average") val average : Int,
    @SerializedName("ratings_total") val ratingsTotal : Int

)


data class Summary (

    @SerializedName("short_description") val shortDescription : String,
    @SerializedName("description") val description : String

)

data class SellerEntity (

    @SerializedName("id") val id : Int,
    @SerializedName("name") val name : String,
    @SerializedName("delivery_time") val deliveryTime : String

)