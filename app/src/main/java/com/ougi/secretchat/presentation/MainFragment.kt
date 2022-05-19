package com.ougi.secretchat.presentation

import android.os.Bundle
import android.view.View
import com.ougi.corecommon.base.view.BaseFragment
import com.ougi.encryptionimpl.di.EncryptionFeatureComponentHolder
import com.ougi.secretchat.databinding.FragmentMainBinding

class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        EncryptionFeatureComponentHolder.getInstance().keyGenerationUtils.secretKey
    }

}