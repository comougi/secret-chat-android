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
import com.ougi.ui.utils.ViewUtils.showAndHide
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


class CreatePasswordFragment :
    BaseFragment<FragmentPasswordBinding>(FragmentPasswordBinding::inflate) {

    private val viewModel: CreatePasswordFragmentViewModel by viewModels { viewModelFactory }

    @Inject
    lateinit var viewModelFactory: CreatePasswordFragmentViewModel.Factory

    @Inject
    lateinit var registrationDialogStarter: RegistrationDialogStarter

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

    override fun onStart() {
        super.onStart()
        firstPasswordInput = null
        secondPasswordInput = null
        setDescriptionTextViewParams()
        with(binding) {
            passwordEditText.clearText()
            errorTextView.visibility = View.GONE
        }
    }

    private fun setDescriptionTextViewParams() {
        with(binding.descriptionTextView) {
            setText(R.string.enter_new_password)
        }
    }

    private fun setPasswordEditTextParams() {
        with(binding.passwordEditText) {
            onInputFinished = { password ->
                binding.errorTextView.visibility = View.GONE
                if (firstPasswordInput == null) onFirstPasswordInputFinished(password)
                else onSecondPasswordInput(password)
            }
        }
    }

    private fun onFirstPasswordInputFinished(password: String) {
        with(binding) {
            passwordEditText.isFocusable = false
            lifecycleScope.launch {
                firstPasswordInput = password
                passwordEditText.updateTextColor(com.ougi.ui.R.color.success_green)
                delay(500)
                passwordEditText.clearText()
                descriptionTextView.setText(R.string.enter_password_again)
            }
            passwordEditText.isFocusableInTouchMode = true
        }
    }

    private fun onSecondPasswordInput(password: String) {

        secondPasswordInput = password
        with(binding) {
            passwordEditText.isFocusable = false
            lifecycleScope.launch {
                if (firstPasswordInput == secondPasswordInput) {
                    passwordEditText.updateTextColor(com.ougi.ui.R.color.success_green)
                    viewModel.savePassword(password)
                    val isOnStart = requireArguments()
                        .getBoolean(PasswordScreenActivity.IS_ON_START, false)
                    registrationDialogStarter.start(this@CreatePasswordFragment, isOnStart)
                } else {
                    firstPasswordInput = null
                    secondPasswordInput = null
                    passwordEditText.updateTextColor(com.ougi.ui.R.color.error_red)
                    errorTextView.setText(R.string.passwords_dont_match)
                    launch {
                        errorTextView.showAndHide(2000)
                    }
                    delay(500)
                    passwordEditText.clearText()
                    descriptionTextView.setText(R.string.enter_new_password)
                }
                passwordEditText.isFocusableInTouchMode = true
            }
        }
    }

}