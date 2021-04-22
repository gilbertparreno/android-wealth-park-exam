package com.wealthpark.exam.main.fragments

import android.content.Context
import android.os.Bundle
import com.wealthpark.exam.R
import com.wealthpark.exam.WealthParkApplication
import com.wealthpark.exam.core.base.BaseFragmentLifeCycle
import com.wealthpark.exam.core.events.FoodsAndCitiesSynchronizationEvent
import com.wealthpark.exam.core.extensions.addFragment
import com.wealthpark.exam.core.extensions.showErrorSnackbar
import com.wealthpark.exam.core.networking.taskStatus.TaskStatus
import com.wealthpark.exam.details.configurations.DetailsConfiguration
import com.wealthpark.exam.details.fragments.DetailsFragment
import com.wealthpark.exam.main.entities.MainListData
import com.wealthpark.exam.main.viewModels.MainViewModel
import com.wealthpark.exam.main.views.MainView
import com.wealthpark.exam.main.views.MainViewDelegate
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MainFragment : BaseFragmentLifeCycle<MainViewModel, MainView>(), MainViewDelegate {

    override fun inject() {
        WealthParkApplication.appComponent.inject(this)
    }

    override fun onCreateView(
        context: Context,
        savedInstanceState: Bundle?
    ) = MainView(context).also {
        it.isRefreshing = true
        it.delegate = this
    }

    override fun observerChanges() {
        viewModel.statusEvent.observe(this) {
            when (it) {
                is TaskStatus.SuccessWithResult -> {
                    contentView.setItems(it.result)
                    contentView.isRefreshing = false
                }
                is TaskStatus.Failure -> {
                    contentView.isRefreshing = false
                    contentView.showErrorSnackbar(
                        it.error.message ?: getString(R.string.generic_error)
                    )
                }
            }
        }
    }

    override fun onViewCreated(contentView: MainView, savedInstanceState: Bundle?) {

    }

    // MainViewDelegate

    override fun onRefreshList() {
        viewModel.getLatestFoodsAndCities(false)
    }

    override fun onItemClicked(item: MainListData) {
        val detailsConfig = when (item) {
            is MainListData.CityListItem -> {
                DetailsConfiguration(item.id, item.imageUrl, item.name, item.description)
            }
            is MainListData.FoodListItem -> {
                DetailsConfiguration(item.id, item.imageUrl, item.name)
            }
            else -> return
        }
        childFragmentManager.beginTransaction().addFragment(
            containerId = R.id.mainFragmentContainer,
            fragmentClass = DetailsFragment::class.java,
            bundle = Bundle().also { it.putSerializable("details_config", detailsConfig) },
            addToBackStack = true
        ).commit()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: FoodsAndCitiesSynchronizationEvent) {
        eventBus.removeStickyEvent(event)
        viewModel.getLatestFoodsAndCities()
    }
}