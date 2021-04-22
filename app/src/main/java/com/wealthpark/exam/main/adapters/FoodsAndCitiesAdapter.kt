package com.wealthpark.exam.main.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import coil.load
import com.wealthpark.exam.R
import com.wealthpark.exam.core.base.BaseRecyclerViewAdapter
import com.wealthpark.exam.core.extensions.dp
import com.wealthpark.exam.core.extensions.getString
import com.wealthpark.exam.core.extensions.inflate
import com.wealthpark.exam.core.extensions.setDebounceClickListener
import com.wealthpark.exam.main.adapters.callbacks.FoodsAndCitiesCallback
import com.wealthpark.exam.main.entities.MainListData
import com.wealthpark.exam.main.entities.MainListData.*
import kotlinx.android.synthetic.main.item_city.view.*
import kotlinx.android.synthetic.main.item_food.view.*

class FoodsAndCitiesAdapter(
    override val items: MutableList<MainListData> = mutableListOf(),
    private val onItemClicked: ((MainListData) -> Unit)? = null
) : BaseRecyclerViewAdapter<MainListData>(items) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            parent.inflate(
                when (items[viewType]) {
                    is CityListItem -> R.layout.item_city
                    is FoodListItem -> R.layout.item_food
                    else -> R.layout.item_header
                }
            )
        )
    }

    override fun onBindViewHolder(view: View, item: MainListData, position: Int) {
        with(view) {
            when (item) {
                is CityListItem -> {
                    cityImage.load(item.imageUrl)
                    cityName.text = item.name
                    cityDescription.text = item.description
                    cityCardView.setDebounceClickListener {
                        onItemClicked?.invoke(item)
                    }
                }
                is FoodListItem -> {
                    foodImage.load(item.imageUrl)
                    foodName.text = item.name
                    foodCardView.setDebounceClickListener {
                        onItemClicked?.invoke(item)
                    }
                }
                is ListHeader -> {
                    (view as TextView).text = getString(item.headerRes)
                }
            }

            if (items.lastOrNull() == item) {
                setPadding(paddingLeft, paddingBottom, paddingRight, dp(16))
            }
        }
    }

    fun setItems(items: List<MainListData>) {
        val callback = DiffUtil.calculateDiff(FoodsAndCitiesCallback(this.items, items))
        this.items.clear()
        this.items.addAll(items)
        callback.dispatchUpdatesTo(this)
    }

    override fun getItemViewType(position: Int) = position
}