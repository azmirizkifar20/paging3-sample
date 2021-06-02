package com.example.paging3.network.response

import android.os.Parcelable
import com.example.paging3.network.response.Product
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductsResponse(
    @field:SerializedName("code")
    val code: Int? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("data")
    val data: List<Product>? = listOf()
) : Parcelable