package com.ougi.passwordscreenimpl.presentation.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.ougi.corecommon.base.view.BaseActivity
import com.ougi.passwordscreenimpl.R
import com.ougi.passwordscreenimpl.databinding.ActivityPasswordScreenBinding
import com.ougi.passwordscreenimpl.di.PasswordScreenComponentHolder
import com.ougi.passwordscreenimpl.presentation.viewmodel.PasswordScreenActivityViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class PasswordScreenActivity :
    BaseActivity<ActivityPasswordScreenBinding>(ActivityPasswordScreenBinding::inflate) {

    private val viewModel: PasswordScreenActivityViewModel by viewModels { viewModelFactory }

    @Inject
    lateinit var viewModelFactory: PasswordScreenActivityViewModel.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        PasswordScreenComponentHolder.getInstance().inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        commitFragment()
    }

    private fun commitFragment() {
        lifecycleScope.launch {
            val startDestination =
                if (viewModel.isHasPassword()) R.id.enterPasswordFragment
                else R.id.createPasswordFragment

            val navController = findNavController(binding.passwordScreenFragmentContainer.id)

            val graph = navController.navInflater.inflate(R.navigation.password_screen_nav_graph)
            graph.setStartDestination(startDestination)

            val isOnStart = intent.getBooleanExtra(IS_ON_START, true)
            val arguments = bundleOf(IS_ON_START to isOnStart)

            navController.setGraph(graph, arguments)
        }
    }

    companion object {
        const val IS_ON_START = "isOnStart"
    }
}