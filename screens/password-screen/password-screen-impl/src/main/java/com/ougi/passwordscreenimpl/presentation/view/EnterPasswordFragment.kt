package com.ougi.passwordscreenimpl.presentation.view

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ougi.corecommon.base.view.BaseFragment
import com.ougi.passwordscreenimpl.R
import com.ougi.passwordscreenimpl.databinding.FragmentPasswordBinding
import com.ougi.passwordscreenimpl.di.PasswordScreenComponentHolder
import com.ougi.passwordscreenimpl.presentation.viewmodel.EnterPasswordFragmentViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


class EnterPasswordFragment :
    BaseFragment<FragmentPasswordBinding>(FragmentPasswordBinding::inflate) {

    private val viewModel: EnterPasswordFragmentViewModel by viewModels { viewModelFactory }

    @Inject
    lateinit var viewModelFactory: EnterPasswordFragmentViewModel.Factory

    @Inject
    lateinit var registrationDialogStarter: RegistrationDialogStarter

    override fun onAttach(context: Context) {
        PasswordScreenComponentHolder.getInstance().inject(this)
        super.onAttach(context)
    }

    override fun FragmentPasswordBinding.initialize() {
        checkHasPassword()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setDescriptionTextViewParams()
        setPasswordEditTextParams()
    }

    private fun checkHasPassword() {
        lifecycleScope.launch {
            if (!viewModel.hasPassword()) {
                findNavController().navigate(R.id.actionEnterPasswordToCreatePassword)
            }
        }
    }

    private fun setDescriptionTextViewParams() {
        with(binding.descriptionTextView) {
            setText(R.string.enter_password)
        }
    }

    private fun setPasswordEditTextParams() {
        with(binding.passwordEditText) {
            clearText()
            onInputFinished = { password ->
                lifecycleScope.launch {
                    val isValid = viewModel.isPasswordValid(password)
                    if (isValid) {
                        updateTextColor(com.ougi.ui.R.color.success_green)
                        lifecycleScope.launch {
                            val isOnStart = requireArguments()
                                .getBoolean(PasswordScreenActivity.IS_ON_START, false)
                            registrationDialogStarter
                                .start(this@EnterPasswordFragment, isOnStart)
                        }
                    } else {
                        updateTextColor(com.ougi.ui.R.color.error_red)
                        binding.descriptionTextView.setText(R.string.enter_password_again)
                        delay(500)
                        clearText()
                    }
                }
            }
        }
    }

}