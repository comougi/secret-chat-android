package com.ougi.chatlistscreenimpl.presentation.view

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import com.ougi.chatlistscreenimpl.databinding.FragmentCreateChatDialogBinding
import com.ougi.corecommon.base.view.BaseDialogFragment
import com.ougi.ui.R as uiR


class CreateChatDialogFragment :
    BaseDialogFragment<FragmentCreateChatDialogBinding>(FragmentCreateChatDialogBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setDialogParams()
    }

    private fun setDialogParams() {
        val window = dialog?.window
        val displayMetrics = resources.displayMetrics
        val dialogWidth = (displayMetrics.widthPixels * 0.97).toInt()
        window?.setLayout(dialogWidth, ViewGroup.LayoutParams.WRAP_CONTENT)

        window?.setBackgroundDrawableResource(uiR.drawable.rounded_container)
    }

}