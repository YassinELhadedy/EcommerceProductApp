package com.jumia.myapplication.domain

import kotlinx.coroutines.flow.Flow


/**
 * PutRepository
 */
interface PutRepository<in T> {
    fun update(entity: T): Flow<Unit>
}