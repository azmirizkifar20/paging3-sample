package com.example.paging3.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.paging3.network.ApiService
import com.example.paging3.network.Notification
import kotlinx.coroutines.flow.Flow

class MainViewModel(
    private val apiService: ApiService
) : ViewModel() {

    fun getNotification(token: String, direction: String): Flow<PagingData<Notification>> =
        Pager(PagingConfig(pageSize = 12)) {
            MainPagingSource(
                token,
                direction,
                this@MainViewModel.apiService
            )
        }.flow.cachedIn(viewModelScope)
}