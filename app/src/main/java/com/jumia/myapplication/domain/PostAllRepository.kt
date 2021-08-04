package com.jumia.myapplication.domain

import kotlinx.coroutines.flow.Flow

/**
 * PostRepository
 */
interface PostAllRepository<in T, out U> {
     fun insertAll(entity: List<T>): Flow<Unit>
}