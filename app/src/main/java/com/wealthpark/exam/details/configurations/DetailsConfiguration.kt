package com.wealthpark.exam.details.configurations

import java.io.Serializable

data class DetailsConfiguration(
    val id: Int,
    var url: String,
    var title: String,
    var subtitle: String? = null
) : Serializable