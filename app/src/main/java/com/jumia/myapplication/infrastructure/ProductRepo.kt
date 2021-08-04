package com.jumia.myapplication.infrastructure

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.jumia.myapplication.domain.GetAllRepository
import com.jumia.myapplication.domain.GetSuspended
import com.jumia.myapplication.domain.Product
import com.jumia.myapplication.infrastructure.dto.JumProduct
import com.jumia.myapplication.infrastructure.dto.JumProductMetadata
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ExperimentalPagingApi
class ProductRepo @Inject constructor(private val service: JumiaWebService) :
    GetAllRepository<PagingData<Product>>, GetSuspended<Product> {

    companion object {
        private const val NETWORK_PAGE_SIZE = 10
    }

    override fun getAllWithPagination(): Flow<PagingData<Product>> {
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = {
                object : AbstractPagingSource<Product>() {
                    override suspend fun fetchData(
                        PageIndex: Int,
                        PageSize: Int
                    ): List<Product> =
                        FlowHandler.flowDataWithErrorHandling<JumProductMetadata> {
                            service.getProducts(
                                PageIndex
                            )
                        }.map { it.toProducts() }.first()

                }.getPagingSource()
            }
        ).flow
    }


    override suspend fun get(id: Int): Product =
        SafeApiCaller.jsonToPojo<JumProduct> {
            service.getProductById(id)
        }.toProduct()
//        SafeApiCaller.jsonToPojo<JumProduct> {
//            SafeApiCaller.safeApiCall { service.getProductById(id) }.metadata
//        }.toProduct()
//        Gson().fromJson(
//        Gson().toJsonTree(SafeApiCaller.safeApiCall { service.getProductById(id) }.metadata).asJsonObject,
//        JumProduct::class.java
//    ).toProduct()
}