package com.jumia.myapplication.domain

import kotlinx.coroutines.flow.Flow

/**
 * DeleteRepository
 */
interface DeleteRepository {
    fun delete(id: Int) : Flow<Unit>
}