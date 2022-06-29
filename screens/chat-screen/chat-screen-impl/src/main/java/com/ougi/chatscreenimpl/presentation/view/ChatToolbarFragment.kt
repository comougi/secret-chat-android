package com.ougi.chatscreenimpl.presentation.view

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.ougi.chatscreenimpl.R
import com.ougi.chatscreenimpl.databinding.FragmentChatToolbarBinding
import com.ougi.chatscreenimpl.di.ChatScreenComponentHolder
import com.ougi.chatscreenimpl.presentation.viewmodel.ChatToolbarFragmentViewModel
import com.ougi.corecommon.base.view.BaseFragment
import kotlinx.coroutines.launch
import javax.inject.Inject

class ChatToolbarFragment :
    BaseFragment<FragmentChatToolbarBinding>(FragmentChatToolbarBinding::inflate) {

    private val chatId: String by lazy {
        requireArguments().getString(ChatScreenFragment.CHAT_ID)!!
    }

    private val viewModel: ChatToolbarFragmentViewModel by viewModels { viewModelFactory }

    @Inject
    lateinit var viewModelFactory: ChatToolbarFragmentViewModel.Factory

    override fun onAttach(context: Context) {
        ChatScreenComponentHolder.getInstance().inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setChatToolbarParams()
    }

    private fun setChatToolbarParams() {
        with(binding.chatToolbar) {
            lifecycleScope.launch {
                title = viewModel.getChatTitle(chatId)
            }

            subtitle = requireContext().getString(R.string.online)
            setSubtitleTextColor(com.ougi.ui.R.color.success_green)
        }
    }

}