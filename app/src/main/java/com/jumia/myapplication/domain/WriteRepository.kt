package com.jumia.myapplication.domain


/**
 * Write Repository with Write Only Methods
 */
interface WriteRepository<in T, out U> : PostRepository<T, U>, PostAllRepository<T, U>,
    PutRepository<T>, DeleteRepository {

    /* A naive implementation for insertOrUpdate that should
     * be enhanced in subclasses
     */
//    @ExperimentalCoroutinesApi
//    suspend fun insertOrUpdate(entity: T): Flow<U> =
//        insert(entity)
//            .map { it }
//            .onEmpty {
//                (update(entity).map { _ ->
//                    @Suppress("UNCHECKED_CAST")
//                    entity as U
//                })
//            }
}