package com.example.paging3.ui

import android.app.SearchManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.paging3.R
import com.example.paging3.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.item_notification.view.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModel()

    // adapter
    private lateinit var adapter: RecommendedAdapter
//    private lateinit var adapter: ReusablePagingAdapter<Product>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.apply {
            setSupportActionBar(this)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.apply {
                addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                statusBarColor = Color.TRANSPARENT
                this.decorView.rootView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        }

        // init utils
        adapter = RecommendedAdapter()
//        adapter = ReusablePagingAdapter(this)

        // setup adapter
//        setupAdapter(binding.rvNotification)

        // init UI
        initUI()
    }

    private fun initUI() {
        lifecycleScope.launch {
            viewModel.recomendedProducts(AUTH_TOKEN, "", "ASC").collect {
                adapter.submitData(it)
            }
        }

        // setup recyclerview
        with(binding.rvRecommended) {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)
            adapter = this@MainActivity.adapter
        }

        with(adapter) {
            // loading state
            addLoadStateListener { loadState ->
                // handle loading
                if (loadState.refresh is LoadState.Loading) {
                    binding.layoutEmpty.visibility = View.GONE
                    binding.layoutError.visibility = View.GONE
                    binding.rvRecommended.visibility = View.GONE
                    binding.shimmerProduct.rootLayout.visibility = View.VISIBLE
                } else if (loadState.append.endOfPaginationReached) {
                    // handle if data is empty
                    if (adapter.itemCount < 1) {
                        binding.layoutEmpty.visibility = View.VISIBLE
                        binding.rvRecommended.visibility = View.GONE
                        binding.shimmerProduct.rootLayout.visibility = View.GONE
                    }
                } else {
                    // handle if data is exists
                    binding.layoutEmpty.visibility = View.GONE
                    binding.rvRecommended.visibility = View.VISIBLE
                    binding.shimmerProduct.rootLayout.visibility = View.GONE

                    // get error
                    val error = when {
                        loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                        loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                        loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                        else -> null
                    }

                    error?.let {
                        binding.layoutEmpty.visibility = View.GONE
                        binding.rvRecommended.visibility = View.GONE
                        binding.layoutError.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_recommended, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search)?.actionView as SearchView

        with(searchView) {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            queryHint = resources.getString(R.string.search_product)

            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    if (p0 != null) {
                        adapter.submitData(lifecycle, PagingData.empty())
                        lifecycleScope.launch {
                            viewModel.recomendedProducts(AUTH_TOKEN, p0, "ASC").collect {
                                adapter.submitData(it)
                            }
                        }

                    }
                    return true
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    if (p0 != null) {
                        adapter.submitData(lifecycle, PagingData.empty())
                        lifecycleScope.launch {
                            viewModel.recomendedProducts(AUTH_TOKEN, p0, "ASC").collect {
                                adapter.submitData(it)
                            }
                        }

                    }
                    return true
                }
            })
        }

        menu.findItem(R.id.search)
            .setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
                override fun onMenuItemActionExpand(p0: MenuItem?): Boolean {
                    adapter.submitData(lifecycle, PagingData.empty())
                    return true
                }

                override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
                    initUI()
                    return true
                }

            })

        return super.onCreateOptionsMenu(menu)
    }

//    private fun setupAdapter(recyclerView: RecyclerView) {
//        adapter.adapterCallback(adapterCallback)
//            .setLayout(R.layout.item_product)
//            .isStaggeredGridView(2)
//            .buildStagged(recyclerView)
//    }
//
//    private val adapterCallback = object : AdapterCallback<Product> {
//        override fun initComponent(itemView: View, data: Product, itemIndex: Int) {
//            // set utils
//            itemView.tv_nama_produk.text = data.name
//
//            // set image
//            Glide.with(this@RecommendedProductActivity)
//                .load(data.thumbnail?.url)
//                .into(itemView.image_product)
//
//            // set status makanan
//            val setStatus = fun(text: String, color: Int) {
//                itemView.tv_status.text = StringBuilder().append(text)
//                itemView.status.setBackgroundResource(color)
//            }
//
//            when (data.visible) {
//                "0" -> setStatus("Habis", R.drawable.status_habis)
//                "1" -> setStatus("Ready", R.drawable.status_ready)
//            }
//
//            // set discount
//            if (data.discount == "0")
//                itemView.tv_harga.text = Helpers.changeToRupiah(data.price!!.toDouble())
//            else {
//                itemView.layout_discount.visibility = View.VISIBLE
//
//                // set new price
//                val oldPrice = data.price!!
//                val discount = oldPrice * data.discount!!.toLong() / 100
//                val newPrice = oldPrice - discount
//
//                // set view
//                itemView.tv_price_before.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
//                itemView.tv_discount.text = StringBuilder().append("${data.discount}% OFF")
//                itemView.tv_price_before.text = Helpers.changeToRupiah(oldPrice.toDouble())
//                itemView.tv_harga.text = Helpers.changeToRupiah(newPrice.toDouble())
//            }
//        }
//
//        override fun onItemClicked(itemView: View, data: Product, itemIndex: Int) { }
//
//    }

    companion object {
        private const val AUTH_TOKEN = "9dbaa191b83ea9a2ab6c639ea78cd107"
    }
}