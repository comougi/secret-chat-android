package com.ougi.passwordscreenimpl.presentation.view

import android.content.Context
import androidx.fragment.app.Fragment
import com.ougi.corecommon.base.ActivityStarter
import com.ougi.passwordscreenimpl.R
import com.ougi.passwordscreenimpl.di.PasswordScreenComponentHolder
import com.ougi.passwordscreenimpl.presentation.viewmodel.UserRegistrationViewModel
import com.ougi.ui.views.LoadingDialog
import javax.inject.Inject

class RegistrationDialogStarter @Inject constructor(
    private val context: Context,
    private val mainActivityScreenStarter: ActivityStarter
) {

    private val viewModel: UserRegistrationViewModel by lazy { factory.create() }

    @Inject
    lateinit var factory: UserRegistrationViewModel.Factory

    init {
        PasswordScreenComponentHolder.getInstance().inject(this)
    }

    suspend fun start(fragment: Fragment, isOnStart: Boolean) {
        if (!viewModel.isRegistered()) {
            val dialog = LoadingDialog()
            dialog.isCancelable = false
            dialog.title = context.getString(R.string.connecting_to_server)
            dialog.action = { viewModel.registerUser() }
            dialog.results = viewModel.idResultStateFlow
            dialog.onSuccess = { onSuccess(isOnStart, fragment, dialog) }
            dialog.show(fragment.parentFragmentManager, null)
        } else
            onSuccess(isOnStart, fragment, null)
    }

    private fun onSuccess(
        isOnStart: Boolean,
        fragment: Fragment,
        dialog: LoadingDialog?,
    ) {
        dialog?.dismiss()

        val activity = fragment.requireActivity()

        if (isOnStart) {
            mainActivityScreenStarter.start()
        } else {
            activity.onBackPressed()
        }
        activity.finish()
    }

}