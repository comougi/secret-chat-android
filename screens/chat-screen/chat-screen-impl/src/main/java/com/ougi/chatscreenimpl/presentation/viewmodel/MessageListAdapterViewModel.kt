package com.ougi.chatscreenimpl.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ougi.chatscreenimpl.domain.usecase.RecycleMessageUseCase
import javax.inject.Inject

interface MessageListAdapterViewModel {
    suspend fun getUserId(): String
    interface Factory : ViewModelProvider.Factory {
        fun create(): MessageListAdapterViewModel
    }
}

class MessageListAdapterViewModelImpl(private val recycleMessageUseCase: RecycleMessageUseCase) :
    ViewModel(), MessageListAdapterViewModel {

    override suspend fun getUserId(): String {
        return recycleMessageUseCase.getUserId()
    }

    class Factory @Inject constructor(private val recycleMessageUseCase: RecycleMessageUseCase) :
        MessageListAdapterViewModel.Factory {
        override fun create(): MessageListAdapterViewModel {
            return create(MessageListAdapterViewModelImpl::class.java)
        }

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MessageListAdapterViewModelImpl(recycleMessageUseCase) as T
        }

    }
}