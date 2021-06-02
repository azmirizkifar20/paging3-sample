package com.example.paging3.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.paging3.network.response.Product
import com.example.paging3.network.service.ApiService
import kotlinx.coroutines.flow.Flow

class MainViewModel(
    private val apiService: ApiService
) : ViewModel() {

    fun recomendedProducts(
        token: String,
        search: String,
        direction: String
    ): Flow<PagingData<Product>> = Pager(PagingConfig(12)) {
        MainPagingSource(token, search, direction, apiService)
    }.flow.cachedIn(viewModelScope)
}