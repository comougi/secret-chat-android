package com.ougi.corecommon.base.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding

internal interface IViewBinding<VB : ViewBinding> {
    val inflate: ((LayoutInflater, ViewGroup?, Boolean) -> VB)
    val binding: VB
    fun VB.initialize() {}
}