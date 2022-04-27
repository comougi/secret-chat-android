package com.ougi.corecommon.base.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding

abstract class BaseDialogFragment<VB : ViewBinding>(
    override val inflate: ((LayoutInflater, ViewGroup?, Boolean) -> VB)
) : DialogFragment(), IViewBinding<VB> {

    private var _binding: VB? = null
    override val binding: VB
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = inflate.invoke(inflater, container, false)
        //TODO : set dialog layout params here
        binding.initialize()
        return binding.root
    }

}