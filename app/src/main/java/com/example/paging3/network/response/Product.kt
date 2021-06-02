package com.example.paging3.network.response

import android.os.Parcelable
import com.example.paging3.network.response.Category
import com.example.paging3.network.response.Media
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(
    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("product_name")
    val name: String? = null,

    @field:SerializedName("product_price")
    val price: Long? = null,

    @field:SerializedName("product_description")
    val description: String? = null,

    @field:SerializedName("discount")
    val discount: String? = null,

    @field:SerializedName("is_visible")
    val visible: String? = null,

    @field:SerializedName("is_recommended")
    val recommended: String? = null,

    @field:SerializedName("category")
    val category: Category? = Category(),

    @field:SerializedName("media")
    val thumbnail: Media? = Media(),

    @field:SerializedName("photos")
    val image: List<Media>? = listOf()
) : Parcelable