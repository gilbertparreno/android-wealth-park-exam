package com.wealthpark.exam.main.adapters.callbacks

import androidx.recyclerview.widget.DiffUtil
import com.wealthpark.exam.core.extensions.allTrue
import com.wealthpark.exam.main.entities.MainListData
import com.wealthpark.exam.main.entities.MainListData.CityListItem
import com.wealthpark.exam.main.entities.MainListData.FoodListItem

class FoodsAndCitiesCallback(
    private val oldList: List<MainListData>,
    private val newList: List<MainListData>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[oldItemPosition]
        return when (oldItem) {
            is CityListItem -> (newItem as? CityListItem)?.id == oldItem.id
            is FoodListItem -> (newItem as? FoodListItem)?.id == oldItem.id
            else -> true
        }
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[oldItemPosition]
        return when (oldItem) {
            is CityListItem -> {
                val item = newItem as? CityListItem
                allTrue(
                    oldItem.id == item?.id,
                    oldItem.name == item?.name,
                    oldItem.description == item?.description,
                    oldItem.imageUrl == item?.imageUrl
                )
            }
            is FoodListItem -> {
                val item = newItem as? FoodListItem
                allTrue(
                    oldItem.id == item?.id,
                    oldItem.name == item?.name,
                    oldItem.imageUrl == item?.imageUrl
                )
            }
            else -> true
        }
    }
}