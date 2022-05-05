package com.ougi.passwordscreenimpl.presentation.view

import com.ougi.corecommon.base.view.BaseActivity
import com.ougi.passwordscreenimpl.databinding.ActivityPasswordScreenBinding

class PasswordScreenActivity :
    BaseActivity<ActivityPasswordScreenBinding>(ActivityPasswordScreenBinding::inflate) {

    var clearBackStack: Boolean = true

    override fun onBackPressed() {
        if (clearBackStack) finishAffinity()
        else super.onBackPressed()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}