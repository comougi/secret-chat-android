package com.ougi.chatlistscreenimpl.presentation.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.commit
import androidx.navigation.findNavController
import com.ougi.chatlistscreenimpl.R
import com.ougi.chatlistscreenimpl.databinding.FragmentChatListScreenBinding
import com.ougi.corecommon.base.view.BaseFragment

class ChatListScreenFragment :
    BaseFragment<FragmentChatListScreenBinding>(FragmentChatListScreenBinding::inflate) {

    override fun FragmentChatListScreenBinding.initialize() {
        with(binding) {
            chatsToolbar.commitFragment(ChatListToolbarFragment::class.java)
            chatListRecyclerView.commitFragment(ChatListFragment::class.java)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAddChatButtonParams()
    }

    private fun FragmentContainerView.commitFragment(fragmentClass: Class<out Fragment>) {
        parentFragmentManager.commit {
            replace(this@commitFragment.id, fragmentClass, null)
        }
    }

    private fun setAddChatButtonParams() {
        with(binding.addChatButton) {
            setOnClickListener {
                binding.chatsToolbar.findNavController()
                    .navigate(R.id.chatListScreenFragmentToCreateChatDialogFragment)
            }
        }
    }

}