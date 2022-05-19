package com.ougi.passwordscreenimpl.presentation.view

import android.content.Context
import androidx.fragment.app.Fragment
import com.ougi.passwordscreenimpl.R
import com.ougi.passwordscreenimpl.di.PasswordScreenComponentHolder
import com.ougi.passwordscreenimpl.presentation.viewmodel.UserRegistrationViewModel
import com.ougi.ui.views.LoadingDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class RegistrationDialogStarter @Inject constructor(private val context: Context) {

    private val viewModel: UserRegistrationViewModel by lazy { factory.create() }

    @Inject
    lateinit var factory: UserRegistrationViewModel.Factory

    init {
        PasswordScreenComponentHolder.getInstance().inject(this)
    }

    suspend fun start(fragment: Fragment) {
        val activity = fragment.requireActivity() as PasswordScreenActivity
        if (!viewModel.isRegistered()) {
            val dialog = LoadingDialog()
            dialog.title = context.getString(R.string.connecting_to_server)
            dialog.action = { viewModel.registerUser() }
            dialog.results = viewModel.idResultStateFlow
            dialog.onSuccess = { onSuccess(activity, dialog) }
            dialog.show(activity.supportFragmentManager, null)
        } else
            onSuccess(activity, null)
    }

    private fun onSuccess(activity: PasswordScreenActivity, dialog: LoadingDialog?) {
        dialog?.dismiss()
        activity.clearBackStack = false
        CoroutineScope(Dispatchers.Main).launch {
            delay(500)
            activity.onBackPressed()
        }
    }

}