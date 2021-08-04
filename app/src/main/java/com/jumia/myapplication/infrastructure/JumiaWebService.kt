package com.jumia.myapplication.infrastructure

import com.jumia.myapplication.infrastructure.dto.AppConfigurationResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface JumiaWebService {

    @GET("/search/phone/page/{id}/")
    suspend fun getProducts( @Path("id") id: Int): AppResponse

    @GET("/product/{id}/")
    suspend fun getProductById( @Path("id") id: Int): AppResponse

    @GET("/configurations/")
    suspend fun getConfiguration(): AppConfigurationResponse
}