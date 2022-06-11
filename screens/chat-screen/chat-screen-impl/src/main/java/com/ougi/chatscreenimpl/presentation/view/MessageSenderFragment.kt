package com.ougi.chatscreenimpl.presentation.view

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.ougi.chatscreenimpl.databinding.FragmentMessageSenderBinding
import com.ougi.chatscreenimpl.di.ChatScreenComponentHolder
import com.ougi.chatscreenimpl.presentation.viewmodel.MessageSenderFragmentViewModel
import com.ougi.corecommon.base.view.BaseFragment
import javax.inject.Inject

class MessageSenderFragment :
    BaseFragment<FragmentMessageSenderBinding>(FragmentMessageSenderBinding::inflate) {

    private val chatId: String by lazy {
        requireArguments().getString(ChatScreenFragment.CHAT_ID)!!
    }
    private val viewModel: MessageSenderFragmentViewModel by viewModels {
        viewModelFactoryFactory.create(chatId)
    }

    @Inject
    lateinit var viewModelFactoryFactory: MessageSenderFragmentViewModel.Factory

    override fun onAttach(context: Context) {
        ChatScreenComponentHolder.getInstance().inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sendMessageButtonParams()
    }

    private fun sendMessageButtonParams() {
        with(binding) {
            sendMessageButton.setOnClickListener {
                val messageText = messageInputEditText.text.toString()
                messageInputEditText.editableText.clear()
                viewModel.sendMessage(messageText)
            }
        }
    }

}