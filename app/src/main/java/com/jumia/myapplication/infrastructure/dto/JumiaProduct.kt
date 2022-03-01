package com.jumia.myapplication.infrastructure.dto

import com.google.gson.annotations.SerializedName
import com.jumia.myapplication.domain.Address
import com.jumia.myapplication.domain.Product
import com.jumia.myapplication.domain.Store
import com.jumia.myapplication.domain.StoreStatus
import kotlin.random.Random

data class JumProductMetadata(
    @SerializedName("sort") val sort: String,
    @SerializedName("total_products") val totalProducts: Int,
    @SerializedName("title") val title: String,
    @SerializedName("results") val products: List<JumProduct>
) {
    fun toProducts(): List<Product> = products.map {
        it.toProduct()
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
    @SerializedName("rating") val rating: Rating?,
    @SerializedName("image_list") val imageList: List<String>?,
    @SerializedName("summary") val summary: Summary?,
    @SerializedName("seller_entity") val sellerEntity: SellerEntity?

) {
    companion object {
        fun Product.toJumProduct(): JumProduct = JumProduct(
            store.name,
            store.name,
            store.name,
            store.name.toInt(),
            store.name.toInt(),
            store.name.toInt(),
            image,
            store.name.toInt(),
            null,
            null,
            null,
            null
        )
    }

    fun toProduct(): Product = Product(
        name,sku,
        4.2,
        88,
        184,
        listOf("https://images.pexels.com/photos/992734/pexels-photo-992734.jpeg?cs=srgb&dl=pexels-brett-sayles-992734.jpg&fm=jpg",
        "https://images.pexels.com/photos/459225/pexels-photo-459225.jpeg?cs=srgb&dl=pexels-pixabay-459225.jpg&fm=jpg",
        "https://images.pexels.com/photos/346529/pexels-photo-346529.jpeg?cs=srgb&dl=pexels-bri-schneiter-346529.jpg&fm=jpg",
        "https://images.pexels.com/photos/884788/pexels-photo-884788.jpeg?cs=srgb&dl=pexels-david-besh-884788.jpg&fm=jpg")[Random.nextInt(0, 3)],
        Random.nextInt(0, 10).toDouble(),
        Random.nextBoolean(),
        Random.nextBoolean(),
        Store(
            "152",
            "Woman,Kids,Men",
            listOf("https://dlskits.com/wp-content/uploads/2018/01/Chelsea-Dream-League-Soccer-Logo.png","" +
                    "https://dlskits.com/wp-content/uploads/2018/05/Aresenal-512x512-Logo-Dream-League-Soccer.png",
                "https://ftsdlskits.com/wp-content/uploads/2018/12/Paris-Saint-Germain-PSG-Logo-512%C3%97512-URL.png",
                "https://ftsdlskits.com/wp-content/uploads/2019/01/Bayern-M%C3%BCnchen-logo-url-512x512-300x300-1.png"
            )[Random.nextInt(0, 3)],
            listOf("Zara","H&M","Nike")[Random.nextInt(0, 2)],
            StoreStatus.fromInt(Random.nextInt(0, 3)),
            "Open\nfrom 8:00 to 22:00",
            Address("3 Km", "15 Min")
        )
    )
}

data class Rating(

    @SerializedName("average") val average: Int,
    @SerializedName("ratings_total") val ratingsTotal: Int

)


data class Summary(

    @SerializedName("short_description") val shortDescription: String,
    @SerializedName("description") val description: String

)

data class SellerEntity(

    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("delivery_time") val deliveryTime: String

)