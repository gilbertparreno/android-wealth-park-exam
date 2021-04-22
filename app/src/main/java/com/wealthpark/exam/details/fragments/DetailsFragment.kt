package com.wealthpark.exam.details.fragments

import android.content.Context
import android.os.Bundle
import com.wealthpark.exam.WealthParkApplication
import com.wealthpark.exam.core.base.BaseFragment
import com.wealthpark.exam.details.configurations.DetailsConfiguration
import com.wealthpark.exam.details.views.DetailsView
import com.wealthpark.exam.details.views.DetailsViewDelegate

class DetailsFragment : BaseFragment<DetailsView>(), DetailsViewDelegate {

    private var detailsConfiguration: DetailsConfiguration? = null
        get() {
            return arguments?.getSerializable("details_config") as DetailsConfiguration
        }

    override fun inject() {
        WealthParkApplication.appComponent.inject(this)
    }

    override fun onCreateView(
        context: Context,
        savedInstanceState: Bundle?
    ) = DetailsView(context).also {
        it.delegate = this
    }

    override fun onViewCreated(contentView: DetailsView, savedInstanceState: Bundle?) {
        detailsConfiguration?.let {
            contentView.setUpView(it)
        }
    }

    // DetailsViewDelegate

    override fun onViewBackPressed() {
        super.onBackPressed()
    }
}