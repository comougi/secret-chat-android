package com.ougi.chatscreenimpl.di

import com.ougi.chatscreenapi.data.ChatScreenStarter
import com.ougi.chatscreenimpl.ChatScreenStarterImpl
import com.ougi.chatscreenimpl.domain.usecase.*
import com.ougi.chatscreenimpl.presentation.viewmodel.*
import com.ougi.coreutils.dagger.Feature
import dagger.Binds
import dagger.Module

@Module
interface ChatScreenModule {

    @[Feature Binds]
    fun bindChatScreenStarterFactory(factory: ChatScreenStarterImpl.Factory): ChatScreenStarter.Factory


    @[Feature Binds]
    fun bindSendMessageUseCase(sendMessageUseCaseImpl: SendMessageUseCaseImpl): SendMessageUseCase

    @[Feature Binds]
    fun bindMessageSenderFragmentFactoryAssistedFactory(factory: MessageSenderFragmentViewModelImpl.AssistedFactory): MessageSenderFragmentViewModel.Factory


    @[Feature Binds]
    fun bindGetMessageListUseCaseFactory(factory: GetMessageListUseCaseImpl.Factory): GetMessageListUseCase.Factory

    @[Feature Binds]
    fun bindMessageListFragmentViewModelFactory(factory: MessageListFragmentViewModelImpl.AssistedFactory): MessageListFragmentViewModel.Factory


    @[Feature Binds]
    fun bindRecycleMessageUseCase(recycleMessageUseCaseImpl: RecycleMessageUseCaseImpl): RecycleMessageUseCase

    @[Feature Binds]
    fun bindMessageListAdapterViewModelFactory(factory: MessageListAdapterViewModelImpl.Factory): MessageListAdapterViewModel.Factory
}