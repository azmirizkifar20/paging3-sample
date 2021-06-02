package com.example.paging3.network.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Media(
    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("file_name")
    val name: String? = null,

    @field:SerializedName("url")
    val url: String? = null
) : Parcelable