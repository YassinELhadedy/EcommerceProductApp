package com.jumia.myapplication.infrastructure

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import androidx.paging.PagingState


abstract class AbstractPagingSource<U : Any> {
    open var STARTING_PAGE_INDEX = 0

    @ExperimentalPagingApi
    fun getPagingSource(): PagingSource<Int, U> {
        return object : PagingSource<Int, U>() {
            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, U> {
                val position = params.key ?: STARTING_PAGE_INDEX
                return try {
                    val response = fetchData(position, params.loadSize)

                    LoadResult.Page(
                            data = response,
                            prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                            nextKey = if (response.isEmpty()) null else position + 1
                    )

                } catch (exception: Throwable) {
                    LoadResult.Error<Int, U>(InfrastructureException(exception))
                }
            }

            override fun getRefreshKey(state: PagingState<Int, U>): Int? {
                return state.anchorPosition?.let { anchorPosition ->
                    val anchorPage = state.closestPageToPosition(anchorPosition)
                    anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
                }
            }
        }
    }

    abstract suspend fun fetchData(PageIndex: Int, PageSize: Int): List<U>
}
