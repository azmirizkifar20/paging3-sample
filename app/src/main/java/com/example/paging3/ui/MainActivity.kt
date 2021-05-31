package com.example.paging3.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.example.paging3.R
import com.example.paging3.databinding.ActivityMainBinding
import com.example.paging3.network.Notification
import com.example.paging3.utils.adapter.AdapterCallback
import com.example.paging3.utils.adapter.LoadingStateAdapter
import com.example.paging3.utils.adapter.ReusablePagingAdapter
import com.example.paging3.utils.helper.Helpers
import kotlinx.android.synthetic.main.item_notification.view.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModel()

    // adapter
    private lateinit var adapter: ReusablePagingAdapter<Notification>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // init utils
        adapter = ReusablePagingAdapter(this)

        // setup adapter
        setupAdapter(binding.rvNotification)

        // init UI
        initUI()
    }

    private fun initUI() {
        lifecycleScope.launch {
            viewModel.getNotification(AUTH_TOKEN, "DESC").collect {
                adapter.submitData(it)
            }
        }

        with(adapter) {
            // loading adapter
            withLoadStateHeaderAndFooter(
                header = LoadingStateAdapter(adapter::retry),
                footer = LoadingStateAdapter(adapter::retry)
            )

            // loading state
            addLoadStateListener { loadState ->
                // handle loading
                if (loadState.refresh is LoadState.Loading) {
                    binding.rvNotification.visibility = View.GONE
                    binding.shimmerNotification.rootLayout.visibility = View.VISIBLE
                } else if (loadState.append.endOfPaginationReached) {
                    // handle if data is empty
                    if (adapter.itemCount < 1) {
                        binding.rvNotification.visibility = View.GONE
                        binding.layoutEmpty.visibility = View.VISIBLE
                    }
                } else {
                    // handle if data is exists
                    binding.rvNotification.visibility = View.VISIBLE
                    binding.shimmerNotification.rootLayout.visibility = View.GONE

                    // get error
                    val error = when {
                        loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                        loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                        loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                        else -> null
                    }

                    error?.let {
                        binding.layoutError.visibility = View.VISIBLE
                        binding.rvNotification.visibility = View.GONE
                        binding.layoutEmpty.visibility = View.GONE
                    }
                }
            }
        }
    }

    private fun setupAdapter(recyclerView: RecyclerView) {
        adapter.adapterCallback(adapterCallback)
            .setLayout(R.layout.item_notification)
            .isVerticalView()
            .build(recyclerView)
    }

    private val adapterCallback = object : AdapterCallback<Notification> {
        override fun initComponent(itemView: View, data: Notification, itemIndex: Int) {
            itemView.tv_title.text = data.title
            itemView.tv_content.text = data.content
            itemView.tv_time.text = Helpers.convertToDateTime(data.date!!)
        }

        override fun onItemClicked(itemView: View, data: Notification, itemIndex: Int) {}

    }

    companion object {
        private const val AUTH_TOKEN = "9dbaa191b83ea9a2ab6c639ea78cd107"
    }
}