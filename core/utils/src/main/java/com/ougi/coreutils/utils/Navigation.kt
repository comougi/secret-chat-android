package com.ougi.coreutils.utils

import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController

object Navigation {

    fun Fragment.navigateTo(deepLinkUri: String) {
        val request = NavDeepLinkRequest.Builder
            .fromUri(deepLinkUri.toUri())
            .build()
        findNavController().navigate(request)
    }
}