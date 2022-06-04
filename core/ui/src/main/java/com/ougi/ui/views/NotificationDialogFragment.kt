package com.ougi.ui.views

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.os.bundleOf
import androidx.core.view.marginTop
import com.ougi.corecommon.base.view.BaseDialogFragment
import com.ougi.ui.R
import com.ougi.ui.databinding.FragmentNotificationDialogBinding

class NotificationDialogFragment :
    BaseDialogFragment<FragmentNotificationDialogBinding>(FragmentNotificationDialogBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setDialogParams()
        setCloseDialogButtonParams()
    }

    override fun onStart() {
        super.onStart()
        val notificationText = requireArguments().getString(NOTIFICATION_TEXT)!!
        setNotificationTextViewParams(notificationText)
    }

    private fun setDialogParams() {
        val window = dialog?.window
        val displayMetrics = resources.displayMetrics
        val dialogWidth = (displayMetrics.widthPixels * 0.97).toInt()
        window?.setLayout(dialogWidth, ViewGroup.LayoutParams.WRAP_CONTENT)

        window?.setBackgroundDrawableResource(R.drawable.rounded_container)
    }

    private fun setNotificationTextViewParams(notificationText: String) {
        val dialogHeight = dialog?.window?.decorView?.height!!

        with(binding.notificationTextView) {
            text = notificationText
            val marginBottom = (dialogHeight - height - 30) / 2
            val currentLayoutParams = layoutParams as LinearLayout.LayoutParams

            currentLayoutParams.run {
                (layoutParams as LinearLayout.LayoutParams)
                    .setMargins(marginStart, marginTop, marginEnd, marginBottom)

            }
        }
    }

    private fun setCloseDialogButtonParams() {
        with(binding.closeDialogButton) {
            setOnClickListener {
                dismiss()
            }
        }
    }

    companion object {
        const val NOTIFICATION_TEXT = "notificationText"
        fun create(notificationText: String): NotificationDialogFragment {
            return NotificationDialogFragment()
                .also { dialogFragment ->
                    dialogFragment.arguments = bundleOf(NOTIFICATION_TEXT to notificationText)
                }
        }
    }

}