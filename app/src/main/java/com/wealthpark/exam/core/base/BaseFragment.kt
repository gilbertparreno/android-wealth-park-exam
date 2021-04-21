package com.wealthpark.exam.core.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import com.wealthpark.exam.core.extensions.getLastFragmentTag
import com.wealthpark.exam.core.extensions.logDebug
import com.wealthpark.exam.core.utils.ViewModelUtils
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.EventBusException
import javax.inject.Inject

abstract class BaseFragment<VM : ViewModel, V : BaseFragmentView> : Fragment(),
    LifecycleOwner {

    @Inject lateinit var viewModel: VM
    @Inject lateinit var eventBus: EventBus
    lateinit var contentView: V

    abstract fun inject()
    abstract fun onCreateView(context: Context, savedInstanceState: Bundle?): V
    abstract fun observerChanges()
    abstract fun onViewCreated(contentView: V, savedInstanceState: Bundle?)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        inject()
        viewModel = ViewModelProvider(ViewModelStore(), ViewModelUtils.createFor(viewModel))
            .get(viewModel::class.java)
        return onCreateView(inflater.context, savedInstanceState).also {
            contentView = it
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        onViewCreated(contentView, savedInstanceState)
        observerChanges()
    }

    open fun onBackPressed(): Boolean {
        val lastFragment = childFragmentManager.findFragmentByTag(
            childFragmentManager.getLastFragmentTag()
        )
        return when {
            childFragmentManager.backStackEntryCount == 0 -> {
                logDebug("popped")
                parentFragmentManager.popBackStack()
                true
            }
            lastFragment?.childFragmentManager?.backStackEntryCount ?: 0 > 0 -> false
            else -> (lastFragment as? BaseFragment<*, *>)?.onBackPressed() ?: false
        }
    }

    override fun onStart() {
        super.onStart()
        try {
            eventBus.register(this)
        } catch (e: EventBusException) {
        }
    }

    override fun onStop() {
        super.onStop()
        eventBus.unregister(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModelStore.clear()
    }
}