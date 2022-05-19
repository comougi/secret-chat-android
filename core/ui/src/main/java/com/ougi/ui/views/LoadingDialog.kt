package com.ougi.ui.views

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import com.ougi.corecommon.base.view.BaseDialogFragment
import com.ougi.coreutils.utils.Result
import com.ougi.coreutils.utils.StringParser
import com.ougi.ui.R
import com.ougi.ui.databinding.LoadingDialogBinding
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoadingDialog : BaseDialogFragment<LoadingDialogBinding>(LoadingDialogBinding::inflate) {

    var title: Any? = null
    var results: StateFlow<Result<*>>? = null
    var action: (() -> Unit)? = null
    var onSuccess: (() -> Unit)? = null
    var onError: (() -> Unit)? = null


    override fun show(manager: FragmentManager, tag: String?) {
        super.show(manager, tag)
        action?.invoke()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setDialogParams()
        setTitleTextViewParams(title)
        setReloadButtonParams()
        collectResults()
    }

    private fun setDialogParams() {
        val window = dialog?.window
        val displayMetrics = resources.displayMetrics
        val dialogWidth = (displayMetrics.widthPixels * 0.97).toInt()
        window?.setLayout(dialogWidth, ViewGroup.LayoutParams.WRAP_CONTENT)
        isCancelable = false

        window?.setBackgroundDrawableResource(R.drawable.rounded_container)
    }

    private fun setTitleTextViewParams(title: Any?) {
        with(binding.titleTextView) {
            val parsedTitle = StringParser.parseToString(title)
            parsedTitle?.let { title -> text = title } ?: { visibility = View.GONE }
        }
    }

    private fun setReloadButtonParams() {
        with(binding.reloadButton) {
            setOnClickListener {
                action?.invoke()
            }
        }
    }

    private fun collectResults() {
        lifecycleScope.launch {
            with(binding) {
                results?.collect { result ->
                    when (result) {
                        is Result.Loading -> {
                            errorTextView.visibility = View.GONE
                            reloadButton.visibility = View.GONE
                            loadingIndicator.visibility = View.VISIBLE
                        }
                        is Result.Success -> {
                            errorTextView.visibility = View.GONE
                            reloadButton.visibility = View.GONE
                            loadingIndicator.visibility = View.GONE
                            onSuccess?.invoke()
                        }
                        is Result.Error -> {
                            loadingIndicator.visibility = View.GONE
                            errorTextView.text = result.message()
                            errorTextView.visibility = View.VISIBLE
                            reloadButton.visibility = View.VISIBLE
                            onError?.invoke()
                        }
                        else -> {
                            errorTextView.visibility = View.GONE
                            reloadButton.visibility = View.GONE
                            loadingIndicator.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }

}