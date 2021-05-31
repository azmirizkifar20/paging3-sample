package com.example.paging3.utils.adapter

import android.view.View

interface AdapterCallback<T> {

    // setup init component
    fun initComponent(itemView: View, data: T, itemIndex: Int)

    // onclick listener
    fun onItemClicked(itemView: View, data: T, itemIndex: Int)
}