package com.example.paging3.network.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Category(
    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("category")
    val category: String? = null,

    @field:SerializedName("slug")
    val slug: String? = null,

    @field:SerializedName("icon")
    val icon: Icon? = Icon(),
) : Parcelable