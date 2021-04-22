package com.wealthpark.exam.details.views

import android.content.Context
import android.view.View
import coil.load
import com.wealthpark.exam.R
import com.wealthpark.exam.core.base.BaseFragmentView
import com.wealthpark.exam.details.configurations.DetailsConfiguration
import kotlinx.android.synthetic.main.view_app_bar.view.*
import kotlinx.android.synthetic.main.view_details.view.*

interface DetailsViewDelegate {
    fun onViewBackPressed()
}

class DetailsView(context: Context) : BaseFragmentView(context) {

    var delegate: DetailsViewDelegate? = null

    init {
        inflate(context, R.layout.view_details, this)
        toolbar.setNavigationIcon(R.drawable.ic_toolbar_back)
        toolbar.setNavigationOnClickListener {
            delegate?.onViewBackPressed()
        }
    }

    fun setUpView(detailsConfiguration: DetailsConfiguration) {
        headerImage.load(detailsConfiguration.url)
        title.text = detailsConfiguration.title
        detailsConfiguration.subtitle?.let {
            subtitle.text = it
        } ?: run {
            subtitle.visibility = View.GONE
        }
    }
}