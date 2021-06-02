package com.example.paging3.network.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Icon(
    @field:SerializedName("icon")
    val icon: String? = null,

    @field:SerializedName("slug")
    val slug: String? = null,

    @field:SerializedName("media")
    val image: Media? = Media()
) : Parcelable