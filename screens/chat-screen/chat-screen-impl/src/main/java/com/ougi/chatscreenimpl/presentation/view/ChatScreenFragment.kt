package com.ougi.chatscreenimpl.presentation.view

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.commit
import androidx.navigation.fragment.navArgs
import com.ougi.chatscreenimpl.databinding.FragmentChatScreenBinding
import com.ougi.corecommon.base.view.BaseFragment

class ChatScreenFragment :
    BaseFragment<FragmentChatScreenBinding>(FragmentChatScreenBinding::inflate) {

    private val navArgs: ChatScreenFragmentArgs by navArgs()

    override fun FragmentChatScreenBinding.initialize() {
        with(binding) {
            messageSenderContainer.commitFragment(MessageSenderFragment::class.java)
            messageListContainer.commitFragment(MessageListFragment::class.java)
        }
    }

    private fun FragmentContainerView.commitFragment(fragmentClass: Class<out Fragment>) {
        val chatId = navArgs.chatId
        parentFragmentManager.commit {
            replace(this@commitFragment.id, fragmentClass, bundleOf(CHAT_ID to chatId))
        }
    }

    companion object {
        const val CHAT_ID = "chatId"
    }

}