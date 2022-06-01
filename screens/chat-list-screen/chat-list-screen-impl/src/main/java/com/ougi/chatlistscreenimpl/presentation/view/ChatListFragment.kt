package com.ougi.chatlistscreenimpl.presentation.view

import android.content.Context
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ougi.chatlistscreenimpl.databinding.FragmentChatListBinding
import com.ougi.chatlistscreenimpl.di.ChatListScreenComponentHolder
import com.ougi.chatlistscreenimpl.presentation.adapter.ChatListAdapter
import com.ougi.chatlistscreenimpl.presentation.viewmodel.ChatListFragmentViewModel
import com.ougi.chatrepoapi.data.entity.Chat
import com.ougi.corecommon.base.view.BaseFragment
import com.ougi.coreutils.utils.Result
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class ChatListFragment :
    BaseFragment<FragmentChatListBinding>(FragmentChatListBinding::inflate) {

    private val viewModel: ChatListFragmentViewModel by viewModels { viewModelFactory }

    @Inject
    lateinit var viewModelFactory: ChatListFragmentViewModel.Factory

    override fun onAttach(context: Context) {
        ChatListScreenComponentHolder.getInstance().inject(this)
        super.onAttach(context)
    }

    override fun onStart() {
        super.onStart()
        collectConnectionState()
    }

    private fun collectConnectionState() {
        viewModel.chatListResultFlow
            .flowWithLifecycle(lifecycle)
            .onEach { result ->
                setChatListRecyclerViewParams(result)
                setLoadingIndicatorParams(result)
                setNoChatsTextView(result)
            }
            .launchIn(lifecycleScope)
    }

    private fun setChatListRecyclerViewParams(result: Result<List<Chat>?>) {
        val chatListAdapter = ChatListAdapter()
        with(binding.chatListRecyclerView) {
            adapter = chatListAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

            visibility = if (result is Result.Success && result.data!!.isNotEmpty()) {
                chatListAdapter.submitList(result.data)
                View.VISIBLE
            } else View.GONE
        }
    }

    private fun setLoadingIndicatorParams(result: Result<List<Chat>?>) {
        with(binding.loadingIndicator) {
            visibility = if (result is Result.Loading) View.VISIBLE else View.GONE
        }
    }

    private fun setNoChatsTextView(result: Result<List<Chat>?>) {
        with(binding.noChatsTextView) {
            visibility =
                if (result is Result.Success && result.data!!.isEmpty() || result is Result.Error)
                    View.VISIBLE
                else
                    View.GONE
        }
    }

}