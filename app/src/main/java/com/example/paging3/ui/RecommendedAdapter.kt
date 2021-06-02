package com.example.paging3.ui

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.paging3.R
import com.example.paging3.network.response.Product
import com.example.paging3.utils.helper.Helpers
import kotlinx.android.synthetic.main.item_product.view.*

class RecommendedAdapter internal constructor() :
    PagingDataAdapter<Product, RecommendedAdapter.RecommendedViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = RecommendedViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
    )

    override fun onBindViewHolder(holder: RecommendedViewHolder, position: Int) {
        val product = getItem(position)
        if (product != null)
            holder.bind(product)
    }

    class RecommendedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(data: Product) {
            with(itemView) {
                // set utils
                tv_nama_produk.text = data.name

                // set image
                Glide.with(context)
                    .load(data.thumbnail?.url)
                    .into(image_product)

                // set status makanan
                val setStatus = fun(text: String, color: Int) {
                    tv_status.text = StringBuilder().append(text)
                    status.setBackgroundResource(color)
                }

                when (data.visible) {
                    "0" -> setStatus("Habis", R.drawable.status_habis)
                    "1" -> setStatus("Ready", R.drawable.status_ready)
                }

                // set discount
                if (data.discount == "0")
                    tv_harga.text = Helpers.changeToRupiah(data.price!!.toDouble())
                else {
                    layout_discount.visibility = View.VISIBLE

                    // set new price
                    val oldPrice = data.price!!
                    val discount = oldPrice * data.discount!!.toLong() / 100
                    val newPrice = oldPrice - discount

                    // set view
                    tv_price_before.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                    tv_discount.text = StringBuilder().append("${data.discount}% OFF")
                    tv_price_before.text = Helpers.changeToRupiah(oldPrice.toDouble())
                    tv_harga.text = Helpers.changeToRupiah(newPrice.toDouble())
                }
            }
        }
    }
}