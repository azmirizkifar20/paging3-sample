package com.example.paging3.ui

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.paging3.network.response.Product
import com.example.paging3.network.service.ApiService

class MainPagingSource(
    private val token: String,
    private val search: String,
    private val direction: String,
    private val apiService: ApiService
) : PagingSource<Int, Product>() {
    override fun getRefreshKey(state: PagingState<Int, Product>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> = try {
        val currentPage = params.key ?: FIRST_PAGE_INDEX
        val response = apiService.recomendedProduct(token, currentPage, search, direction)
        val responseList = mutableListOf<Product>()

        val data = response.body()?.data ?: emptyList()
        responseList.addAll(data)

        LoadResult.Page(
            data = responseList,
            prevKey = null,
            nextKey = if (data.isNotEmpty()) currentPage.plus(1)
            else null
        )
    } catch (e: Exception) {
        LoadResult.Error(e)
    }

    companion object {
        private const val FIRST_PAGE_INDEX = 0
    }
}