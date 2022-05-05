package com.ougi.passwordscreenimpl.presentation.view

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.ougi.corecommon.base.view.BaseFragment
import com.ougi.passwordscreenimpl.R
import com.ougi.passwordscreenimpl.databinding.FragmentPasswordBinding
import com.ougi.passwordscreenimpl.di.PasswordScreenComponentHolder
import com.ougi.passwordscreenimpl.presentation.viewmodel.CreatePasswordFragmentViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


class CreatePasswordFragment :
    BaseFragment<FragmentPasswordBinding>(FragmentPasswordBinding::inflate) {

    private val viewModel: CreatePasswordFragmentViewModel by viewModels { viewModelFactory }

    @Inject
    lateinit var viewModelFactory: CreatePasswordFragmentViewModel.Factory

    private var firstPasswordInput: String? = null
    private var secondPasswordInput: String? = null

    override fun onAttach(context: Context) {
        PasswordScreenComponentHolder.getInstance().inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setDescriptionTextViewParams()
        setPasswordEditTextParams()
    }

    private fun setDescriptionTextViewParams() {
        with(binding.descriptionTextView) {
            setText(R.string.enter_new_password)
        }
    }

    private fun setPasswordEditTextParams() {
        with(binding.passwordEditText) {
            clearText()
            onInputFinished = { password ->
                if (firstPasswordInput == null) {
                    onFirstPasswordInput(password)
                } else {
                    onSecondPasswordInput(password)
                }
            }
        }
    }

    private fun onFirstPasswordInput(password: String) {
        with(binding) {
            lifecycleScope.launch {
                firstPasswordInput = password
                passwordEditText.updateTextColor(com.ougi.ui.R.color.success_green)
                delay(500)
                passwordEditText.clearText()
                descriptionTextView.setText(R.string.enter_password_again)
            }
        }
    }

    private fun onSecondPasswordInput(password: String) {
        secondPasswordInput = password
        with(binding) {
            lifecycleScope.launch {
                if (firstPasswordInput == secondPasswordInput) {
                    passwordEditText.updateTextColor(com.ougi.ui.R.color.success_green)
                    viewModel.savePassword(password)
                    (requireActivity() as PasswordScreenActivity).clearBackStack = false
                    delay(500)
                    requireActivity().onBackPressed()
                } else {
                    firstPasswordInput = null
                    secondPasswordInput = null
                    passwordEditText.updateTextColor(com.ougi.ui.R.color.error_red)
                    delay(500)
                    passwordEditText.clearText()
                    descriptionTextView.setText(R.string.enter_new_password)
                }
            }
        }
    }

}