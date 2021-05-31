package com.example.paging3.utils.helper

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkHelper<S>(
    okHttpClient: OkHttpClient,
    serviceClass: Class<S>
) {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    val createService : S by lazy { retrofit.create(serviceClass) }

}