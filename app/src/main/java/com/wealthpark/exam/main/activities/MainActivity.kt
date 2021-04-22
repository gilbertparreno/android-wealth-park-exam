package com.wealthpark.exam.main.activities

import android.os.Bundle
import com.wealthpark.exam.R
import com.wealthpark.exam.WealthParkApplication
import com.wealthpark.exam.core.base.BaseActivity
import com.wealthpark.exam.core.extensions.addFragment
import com.wealthpark.exam.core.extensions.getFragmentTag
import com.wealthpark.exam.main.fragments.MainFragment

class MainActivity : BaseActivity(R.layout.activity_main) {

    override fun inject() {
        WealthParkApplication.appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (supportFragmentManager.fragments.isEmpty()) {
            supportFragmentManager.apply {
                beginTransaction()
                    .addFragment(
                        containerId = R.id.mainContainer,
                        fragmentClass = MainFragment::class.java,
                        addToBackStack = false
                    ).commit()
            }
            rootFragmentTag = getFragmentTag(MainFragment::class.java)
        }
    }
}