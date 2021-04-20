package com.wealthpark.exam.core.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.wealthpark.exam.core.extensions.getLastFragmentTag
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

abstract class BaseActivity(
    @LayoutRes contentLayoutId: Int
) : AppCompatActivity(contentLayoutId) {

    @Inject lateinit var eventBus: EventBus

    open lateinit var rootFragmentTag: String

    abstract fun inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        inject()
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        eventBus.register(this)
    }

    override fun onStop() {
        super.onStop()
        eventBus.unregister(this)
    }

    override fun onBackPressed() {
        val rootFragmentManager = supportFragmentManager
            .findFragmentByTag(rootFragmentTag)
            ?.childFragmentManager
            ?: run {
                super.onBackPressed()
                return
            }
        if (rootFragmentManager.backStackEntryCount > 0) {
            var childFragment = rootFragmentManager.findFragmentByTag(
                rootFragmentManager.getLastFragmentTag()
            ) as? BaseFragment<*, *>
            while (childFragment?.onBackPressed() == false) {
                childFragment = childFragment.childFragmentManager
                    .findFragmentByTag(
                        childFragment.childFragmentManager.getLastFragmentTag()
                    ) as? BaseFragment<*, *>
            }
        } else {
            super.onBackPressed()
        }
    }
}