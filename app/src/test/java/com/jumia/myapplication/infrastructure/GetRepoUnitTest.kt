package com.jumia.myapplication.infrastructure

import androidx.paging.ExperimentalPagingApi
import com.jumia.myapplication.domain.Product
import com.jumia.myapplication.infrastructure.*
import com.jumia.myapplication.infrastructure.dto.JumConfiguration
import com.jumia.myapplication.infrastructure.dto.JumCurrency
import com.jumia.myapplication.infrastructure.dto.JumLanguages
import com.jumia.myapplication.infrastructure.dto.JumProduct.Companion.toJumProduct
import com.jumia.myapplication.infrastructure.dto.JumSupport
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.robolectric.ParameterizedRobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

private const val DATA_ERROR = "service not found"

@ExperimentalPagingApi
@RunWith(ParameterizedRobolectricTestRunner::class)
@Config(sdk = [27])
// parameters declared in the params() are injected through the constructor to the test class
class GetRepoUnitTest(private val param: SetupTestParameter<*>) {

    @Test
    fun addition_isCorrect() {
        // parameter use in a test
        runBlocking {
            val testParameter = param.setup()

            testParameter.getNormalIDs().map {
                val triple = testParameter.getAllWithNormalIDs(it)
                Triple(triple.first, triple.second, triple.third)
            }.forEach {
                assertEquals(it.first, it.third)
            }
        }
    }

    @Test
    fun testGetAllWithFaultyPaginationFromRepository() {
        runBlocking {
            val testParameter = param.setup()

            try {
                testParameter.getFaultyIDs().map {
                    val triple = testParameter.getAllWithFaultyIDs(it)
                    Triple(triple.first, triple.second, triple.third)
                }
            } catch (e: InfrastructureException) {
                Assert.assertEquals(e.cause?.cause?.message,
                   DATA_ERROR
                )
                Assert.assertTrue(e.cause is InfrastructureException)
            }
        }
    }

    companion object {
        @JvmStatic
        // name argument is optional, it will show up on the test results
        @ParameterizedRobolectricTestRunner.Parameters(name = "Input: {0}")
        // parameters are provided as arrays, allowing more than one parameter
        fun params() = listOf(
            object : SetupTestParameter<Product> {
                override fun setup(): TestParameter<Product> {
                    val service2 = Mockito.mock(JumiaWebService::class.java)
                    val products = listOf(
                        Product(
                            null, "S", "S", "S", 1, 1, 1, null, 1, null, null, null, null
                        ),
                        Product(
                            null, "S", "S", "S", 1, 1, 1, null, 1, null, null, null, null
                        )
                    )
                    val filterNormalMap = hashMapOf(1 to products[0], 2 to products[1])
                    val filterFaultMap =
                        hashMapOf<Int, Throwable>(3 to RuntimeException(DATA_ERROR))


                    return object : TestParameter<Product> {
                        override suspend fun getNormalIDs(): Set<Int> = filterNormalMap.keys

                        override suspend fun getAllWithNormalIDs(id: Int): Triple<Product, Int, Product> {
                            Mockito.doReturn(
                                AppResponse(
                                    true, filterNormalMap[id]?.toJumProduct()
                                )
                            )
                                .`when`(service2)
                                .getProductById(ArgumentMatchers.anyInt())
                            val productRepo = ProductRepo(service2)

                            return Triple(
                                productRepo.get(id),
                                id,
                                filterNormalMap[id]!!
                            )
                        }

                        override suspend fun getFaultyIDs(): Set<Int> = filterFaultMap.keys

                        override suspend fun getAllWithFaultyIDs(id: Int): Triple<Product, Int, Throwable> {
                            Mockito.doReturn(
                                AppResponse(
                                    false, null
                                )
                            )
                                .`when`(service2)
                                .getProductById(ArgumentMatchers.anyInt())
                            val productRepo = ProductRepo(service2)

                            return Triple(
                                productRepo.get(id),
                                id,
                                filterFaultMap[id]!!
                            )
                        }

                    }
                }

                override fun toString(): String =
                    ProductRepo::class.java.simpleName
            },
            object : SetupTestParameter<JumConfiguration> {
                override fun setup(): TestParameter<JumConfiguration> {
                    val service2 = Mockito.mock(JumiaWebService::class.java)
                    val jumConfiguration = listOf(
                        JumConfiguration(
                            JumCurrency("iso", "$", 1, 2, "THY", "DEC"),
                            listOf(JumLanguages("code", "name", true)),
                            JumSupport("0196262325", true, "Email")
                        ),
                        JumConfiguration(
                            JumCurrency("iso", "$", 1, 2, "THY", "DEC"),
                            listOf(JumLanguages("code", "name", true)),
                            JumSupport("0196262325", true, "Email")
                        )
                    )
                    val filterNormalMap =
                        hashMapOf(1 to jumConfiguration[0], 2 to jumConfiguration[1])
                    val filterFaultMap =
                        hashMapOf<Int, Throwable>(3 to RuntimeException(DATA_ERROR))
                    return object : TestParameter<JumConfiguration> {
                        override suspend fun getNormalIDs(): Set<Int> = filterNormalMap.keys

                        override suspend fun getAllWithNormalIDs(id: Int): Triple<JumConfiguration, Int, JumConfiguration> {
                            Mockito.doReturn(
                                AppResponse(
                                    true, filterNormalMap[id]
                                )
                            )
                                .`when`(service2)
                                .getConfiguration()
                            val configurationRepo = ConfigurationRepo(service2)

                            return Triple(
                                configurationRepo.get(id),
                                id,
                                filterNormalMap[id]!!
                            )
                        }

                        override suspend fun getFaultyIDs(): Set<Int> = filterFaultMap.keys

                        override suspend fun getAllWithFaultyIDs(id: Int): Triple<JumConfiguration, Int, Throwable> {
                            Mockito.doReturn(
                                AppResponse(
                                    false, null
                                )
                            )
                                .`when`(service2)
                                .getConfiguration()
                            val configurationRepo = ConfigurationRepo(service2)

                            return Triple(
                                configurationRepo.get(id),
                                id,
                                filterFaultMap[id]!!
                            )
                        }
                    }
                }

                override fun toString(): String =
                    ConfigurationRepo::class.java.simpleName
            }
        )
    }


    interface TestParameter<out T> {
        suspend fun getNormalIDs(): Set<Int>
        suspend fun getAllWithNormalIDs(id: Int): Triple<T, Int, T>
        suspend fun getFaultyIDs(): Set<Int>
        suspend fun getAllWithFaultyIDs(id: Int): Triple<T, Int, Throwable>
    }

    interface SetupTestParameter<out T> {
        fun setup(): TestParameter<T>
    }
}