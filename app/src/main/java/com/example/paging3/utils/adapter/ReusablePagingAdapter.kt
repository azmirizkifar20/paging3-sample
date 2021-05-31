package com.example.paging3.utils.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.*
import kotlin.properties.Delegates

class ReusablePagingAdapter<T : Any>(
    private val context: Context
) : PagingDataAdapter<T, ReusablePagingAdapter<T>.ViewHolder>(DiffUtilCallback()) {

    // utils
    private var layout by Delegates.notNull<Int>()
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var staggedLayoutManager: StaggeredGridLayoutManager

    // callback
    private lateinit var adapterCallback: AdapterCallback<T>

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(layout, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { data ->
            adapterCallback.initComponent(holder.itemView, data, position)
            holder.itemView.setOnClickListener {
                adapterCallback.onItemClicked(it, data, position)
            }
        }
    }

    // set layout
    fun setLayout(layout: Int): ReusablePagingAdapter<T> {
        this.layout = layout
        return this
    }

    // callback
    fun adapterCallback(adapterCallback: AdapterCallback<T>): ReusablePagingAdapter<T> {
        this.adapterCallback = adapterCallback
        return this
    }

    // layout manager
    fun isVerticalView(): ReusablePagingAdapter<T> {
        layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL, false
        )
        return this
    }

    fun isHorizontalView(): ReusablePagingAdapter<T> {
        layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL, false
        )
        return this
    }

    fun isGridView(spanCount: Int): ReusablePagingAdapter<T> {
        layoutManager = GridLayoutManager(
            context,
            spanCount, GridLayoutManager.VERTICAL, false
        )
        return this
    }

    fun isStaggeredGridView(spanCount: Int): ReusablePagingAdapter<T> {
        staggedLayoutManager =
            StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL)
        return this
    }

    // build recyclerview
    fun build(recyclerView: RecyclerView): ReusablePagingAdapter<T> {
        recyclerView.apply {
            this.adapter = this@ReusablePagingAdapter
            this.layoutManager = this@ReusablePagingAdapter.layoutManager
        }
        return this
    }

    fun buildStagged(recyclerView: RecyclerView): ReusablePagingAdapter<T> {
        recyclerView.apply {
            this.adapter = this@ReusablePagingAdapter
            this.layoutManager = this@ReusablePagingAdapter.staggedLayoutManager
        }
        return this
    }

    class DiffUtilCallback<T> : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem == newItem
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem == newItem
        }

    }
}