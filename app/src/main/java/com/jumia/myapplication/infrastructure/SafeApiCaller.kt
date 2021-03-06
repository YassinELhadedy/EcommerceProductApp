package com.jumia.myapplication.infrastructure

import com.google.gson.Gson
import com.jumia.myapplication.domain.NotFoundException
import com.jumia.myapplication.domain.UnauthorizedException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

object SafeApiCaller {

    suspend fun <T> safeApiCall(
        dispatcher: CoroutineDispatcher = Dispatchers.Default,
        apiCall: suspend () -> T
    ): T {
        return withContext(dispatcher) {
            try {

                //Store the request original response
                val response: T = apiCall.invoke()
                //Create a copy for response to caste it to AppResponse and make all check on it
                val appResponse = response
                if ((appResponse as AppResponse).success) {
                    response
                } else {
                    throw InfrastructureException("service not found")
                }
            } catch (throwable: Throwable) {
                when (throwable) {
                    is IOException -> {
                        throw NetworkException(throwable)
                    }
                    is HttpException -> {
                        when (throwable.code()) {
                            404 -> {
                                throw NotFoundException(throwable)
                            }
                            401 -> {
                                throw UnauthorizedException(throwable)
                            }
                            else -> {
                                throw InfrastructureException(throwable)
                            }
                        }
                    }
                    else -> {
                        throw InfrastructureException(throwable)
                    }
                }
            }
        }
    }

    suspend inline fun <reified T> jsonToPojo(
        crossinline serviceCall: suspend () -> AppResponse
    ): T = Gson().fromJson(
        Gson().toJsonTree((safeApiCall { serviceCall.invoke() }).metadata).asJsonObject,
        T::class.java
    )
}