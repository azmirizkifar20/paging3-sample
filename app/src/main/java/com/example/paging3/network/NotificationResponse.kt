package com.example.paging3.network

import android.os.Parcelable
import com.example.paging3.network.Notification
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NotificationResponse(
    @field:SerializedName("code")
    val code: Int? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("data")
    val data: List<Notification>? = listOf()
) : Parcelable
