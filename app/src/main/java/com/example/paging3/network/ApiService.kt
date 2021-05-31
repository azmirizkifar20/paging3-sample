package com.example.paging3.network

import com.example.paging3.utils.helper.Constants.ROUTE_NOTIFICATION
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiService {

    @GET(ROUTE_NOTIFICATION)
    suspend fun getNotification(
        @Header("Authorization") authorization: String,
        @Query("page") page: Int,
        @Query("order-direction") direction: String
    ): Response<NotificationResponse>
}