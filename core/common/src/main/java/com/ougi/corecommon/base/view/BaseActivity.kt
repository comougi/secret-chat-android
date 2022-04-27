package com.ougi.corecommon.base.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VB : ViewBinding>
    (override val inflate: (LayoutInflater, ViewGroup?, Boolean) -> VB) :
    IViewBinding<VB>, AppCompatActivity() {

    private var _binding: VB? = null
    override val binding: VB
        get() = requireNotNull(_binding)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = inflate.invoke(layoutInflater, null, false)
        setContentView(binding.root)
        binding.initialize()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}