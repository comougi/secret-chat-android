package com.ougi.chatlistscreenimpl.presentation.view

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.ougi.chatlistscreenimpl.R
import com.ougi.chatlistscreenimpl.databinding.FragmentChatListToolbarBinding
import com.ougi.chatlistscreenimpl.di.ChatListScreenComponentHolder
import com.ougi.chatlistscreenimpl.presentation.viewmodel.ChatListToolbarFragmentViewModel
import com.ougi.corecommon.base.view.BaseFragment
import com.ougi.coreutils.utils.Result
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.ougi.ui.R as uiR

class ChatListToolbarFragment :
    BaseFragment<FragmentChatListToolbarBinding>(FragmentChatListToolbarBinding::inflate) {

    private val viewModel: ChatListToolbarFragmentViewModel by viewModels { viewModelFactory }

    @Inject
    lateinit var viewModelFactory: ChatListToolbarFragmentViewModel.Factory

    override fun onAttach(context: Context) {
        ChatListScreenComponentHolder.getInstance().inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbarSubtitleParams()
    }

    private fun setToolbarSubtitleParams() {
        with(binding.chatListToolbar) {

            lifecycleScope.launch {
                menu.findItem(R.id.userIdItem).actionView.apply {
                    tooltipText = "${getString(R.string.your_id_is)} ${viewModel.getUserId()}"
                    setBackgroundResource(R.drawable.ic_face_48)
                    setOnClickListener {
                        it.performLongClick()
                    }
                }
            }


            viewModel.connectionStateResult
                .flowWithLifecycle(lifecycle)
                .onEach { result ->
                    subtitle = result.message()
                    val subtitleColorRes = when (result) {
                        is Result.Loading -> android.R.color.darker_gray
                        is Result.Success -> uiR.color.success_green
                        is Result.Error -> uiR.color.error_red
                    }
                    val subtitleColorInt = requireContext().getColor(subtitleColorRes)
                    setSubtitleTextColor(subtitleColorInt)
                }
                .launchIn(lifecycleScope)
        }
    }

}