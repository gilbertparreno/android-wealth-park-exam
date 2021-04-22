package com.wealthpark.exam.main.views

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import com.wealthpark.exam.R
import com.wealthpark.exam.core.base.BaseFragmentView
import com.wealthpark.exam.main.adapters.FoodsAndCitiesAdapter
import com.wealthpark.exam.main.entities.MainListData
import kotlinx.android.synthetic.main.view_main.view.*

interface MainViewDelegate {
    fun onRefreshList()
    fun onItemClicked(item: MainListData)
}

class MainView(context: Context) : BaseFragmentView(context) {

    var delegate: MainViewDelegate? = null
    var isRefreshing: Boolean = false
        set(value) {
            field = value
            swipeRefreshLayout.isRefreshing = value
        }
        get() = swipeRefreshLayout.isRefreshing

    private val adapter = FoodsAndCitiesAdapter { item ->
        delegate?.onItemClicked(item)
    }

    init {
        inflate(context, R.layout.view_main, this)
        with(foodsAndCitiesList) {
            layoutManager = LinearLayoutManager(context)
            adapter = this@MainView.adapter
        }
        swipeRefreshLayout.setOnRefreshListener {
            delegate?.onRefreshList()
        }
    }

    fun setItems(items: List<MainListData>) {
        adapter.setItems(items)
    }
}