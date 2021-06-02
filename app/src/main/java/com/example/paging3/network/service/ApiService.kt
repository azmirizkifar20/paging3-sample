package com.example.paging3.network.service

import com.example.paging3.network.response.ProductsResponse
import com.example.paging3.utils.helper.Constants.ROUTE_RECOMENDED_PRODUCTS
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiService {

    @GET(ROUTE_RECOMENDED_PRODUCTS)
    suspend fun recomendedProduct(
        @Header("Authorization") authorization: String,
        @Query("page") page: Int,
        @Query("search") search: String,
        @Query("order-direction") direction: String
    ): Response<ProductsResponse>
}