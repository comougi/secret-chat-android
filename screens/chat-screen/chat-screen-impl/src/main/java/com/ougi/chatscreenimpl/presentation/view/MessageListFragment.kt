package com.ougi.chatscreenimpl.presentation.view

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ougi.chatscreenimpl.databinding.FragmentMessageListBinding
import com.ougi.chatscreenimpl.di.ChatScreenComponentHolder
import com.ougi.chatscreenimpl.presentation.adapter.MessageListAdapter
import com.ougi.chatscreenimpl.presentation.viewmodel.MessageListFragmentViewModel
import com.ougi.corecommon.base.view.BaseFragment
import com.ougi.coreutils.utils.Result
import com.ougi.messagerepoapi.data.entities.PersonalMessage
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class MessageListFragment :
    BaseFragment<FragmentMessageListBinding>(FragmentMessageListBinding::inflate) {

    private val chatId: String by lazy {
        requireArguments().getString(ChatScreenFragment.CHAT_ID)!!
    }
    private val viewModel: MessageListFragmentViewModel by viewModels {
        viewModelFactoryFactory.create(chatId)
    }

    @Inject
    lateinit var viewModelFactoryFactory: MessageListFragmentViewModel.Factory

    @Inject
    lateinit var messageListAdapter: MessageListAdapter

    override fun onAttach(context: Context) {
        ChatScreenComponentHolder.getInstance().inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collectMessageList()
        setMessageListRecyclerViewParams()
    }

    private fun setMessageListRecyclerViewParams() {
        with(binding.messageListRecyclerView) {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, true)
            adapter = messageListAdapter
        }
    }

    private fun setMessageListRecyclerViewVisibility(result: Result<*>) {
        with(binding.messageListRecyclerView) {
            visibility = when (result) {
                is Result.Success -> View.VISIBLE
                else -> View.GONE
            }
        }
    }

    private fun collectMessageList() {
        viewModel.personalMessagesResult
            .flowWithLifecycle(lifecycle)
            .onEach { result ->
                if (result is Result.Success)
                    submitNewData(result.data!!)
                setMessageListRecyclerViewVisibility(result)
                setLoadingIndicatorParams(result)
                setEmptyMessageListTextViewParams(result)
            }
            .launchIn(lifecycleScope)
    }

    private fun submitNewData(messages: List<PersonalMessage>) {
        with(binding.messageListRecyclerView) {
            messageListAdapter.submitList(messages)
            binding.messageListRecyclerView.smoothScrollToPosition(0)
        }
    }

    private fun setLoadingIndicatorParams(result: Result<*>) {
        with(binding.loadingIndicator) {
            visibility = when (result) {
                is Result.Loading -> View.VISIBLE
                else -> View.GONE
            }
        }
    }

    private fun setEmptyMessageListTextViewParams(result: Result<List<PersonalMessage>>) {
        with(binding.emptyMessageListTextView) {
            visibility = when {
                result is Result.Error -> View.VISIBLE
                result is Result.Success && result.data!!.isEmpty() -> View.VISIBLE
                else -> View.GONE
            }
        }
    }

}