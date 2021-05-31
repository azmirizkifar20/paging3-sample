package com.example.paging3.ui

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.paging3.network.ApiService
import com.example.paging3.network.Notification

class MainPagingSource(
    private val token: String,
    private val direction: String,
    private val apiService: ApiService
) : PagingSource<Int, Notification>() {
    override fun getRefreshKey(state: PagingState<Int, Notification>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Notification> = try {
        val currentPage = params.key ?: FIRST_PAGE_INDEX
        val response = apiService.getNotification(token, currentPage, direction)
        val responseList = mutableListOf<Notification>()

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