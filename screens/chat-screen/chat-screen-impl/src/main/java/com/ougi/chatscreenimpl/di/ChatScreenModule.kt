package com.ougi.chatscreenimpl.di

import com.ougi.chatscreenapi.data.ChatScreenStarter
import com.ougi.chatscreenimpl.ChatScreenStarterImpl
import com.ougi.chatscreenimpl.domain.usecase.SendMessageUseCase
import com.ougi.chatscreenimpl.domain.usecase.SendMessageUseCaseImpl
import com.ougi.chatscreenimpl.presentation.viewmodel.MessageSenderFragmentViewModel
import com.ougi.chatscreenimpl.presentation.viewmodel.MessageSenderFragmentViewModelImpl
import com.ougi.coreutils.dagger.Feature
import dagger.Binds
import dagger.Module

@Module
interface ChatScreenModule {

    @[Feature Binds]
    fun bindSendMessageUseCase(sendMessageUseCaseImpl: SendMessageUseCaseImpl): SendMessageUseCase

    @[Feature Binds]
    fun bindMessageSenderFragmentFactoryAssistedFactory(factory: MessageSenderFragmentViewModelImpl.AssistedFactory): MessageSenderFragmentViewModel.Factory

    @[Feature Binds]
    fun bindChatScreenStarterFactory(factory: ChatScreenStarterImpl.Factory): ChatScreenStarter.Factory
}