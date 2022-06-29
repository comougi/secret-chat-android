package com.ougi.ui.utils

import android.view.View
import kotlinx.coroutines.delay

object ViewUtils {

    suspend fun View.showAndHide(duration: Long = 500) {
        visibility = View.VISIBLE
        delay(duration)
        visibility = View.GONE
    }

}