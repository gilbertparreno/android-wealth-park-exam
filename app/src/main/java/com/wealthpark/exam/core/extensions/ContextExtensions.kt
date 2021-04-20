package com.wealthpark.exam.core.extensions

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat

fun Context.getCompatDrawable(@DrawableRes id: Int) : Drawable? {
    return ContextCompat.getDrawable(this, id)
}