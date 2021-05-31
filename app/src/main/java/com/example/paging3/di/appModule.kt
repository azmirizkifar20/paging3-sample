package com.example.paging3.di

import com.example.paging3.network.ApiService
import com.example.paging3.ui.MainViewModel
import com.example.paging3.utils.helper.NetworkHelper
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import java.util.concurrent.TimeUnit

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .build()
    }

    single { NetworkHelper(get(), ApiService::class.java).createService }
}

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
}