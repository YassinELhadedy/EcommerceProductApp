package com.jumia.myapplication.domain

import kotlinx.coroutines.flow.Flow

/**
 * GetRepository
 */
interface GetRepository<out T> {
     fun get(id: Int): Flow<T>
}
interface GetSuspended<out T>{
    suspend fun get(id: Int): T

}