package com.wealthpark.exam.main.activities

import android.os.Bundle
import com.wealthpark.exam.R
import com.wealthpark.exam.WealthParkApplication
import com.wealthpark.exam.core.base.BaseActivity
import com.wealthpark.exam.core.events.FoodsAndCitiesSynchronizedEvent
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import timber.log.Timber

class MainActivity : BaseActivity(R.layout.activity_main) {

    override fun inject() {
        WealthParkApplication.appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: FoodsAndCitiesSynchronizedEvent) {
        Timber.d("")
    }
}