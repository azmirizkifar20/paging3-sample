package com.example.paging3.utils.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.paging3.R
import com.example.paging3.databinding.ItemNetworkStateBinding

class LoadingStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<LoadingStateAdapter.NetworkStateItemViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): NetworkStateItemViewHolder = NetworkStateItemViewHolder.create(parent, retry)

    override fun onBindViewHolder(holder: NetworkStateItemViewHolder, loadState: LoadState) =
        holder.bind(loadState)

    class NetworkStateItemViewHolder(
        private val binding: ItemNetworkStateBinding,
        private val retryCallback: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.retryButton.setOnClickListener { retryCallback() }
        }

        fun bind(loadState: LoadState) {
            with(binding) {
                progressBar.isVisible = true
                retryButton.isVisible = loadState is LoadState.Error
                errorMsg.isVisible =
                    !(loadState as? LoadState.Error)?.error?.message.isNullOrBlank()
                errorMsg.text = (loadState as? LoadState.Error)?.error?.message
            }
        }

        companion object {
            fun create(parent: ViewGroup, retry: () -> Unit): NetworkStateItemViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_network_state, parent, false)
                val binding = ItemNetworkStateBinding.bind(view)

                return NetworkStateItemViewHolder(binding, retry)
            }
        }
    }
}