package com.ougi.chatlistscreenimpl.di

import com.ougi.chatlistscreenimpl.data.ChatListRepositoryImpl
import com.ougi.chatlistscreenimpl.data.MessagingConnectionStateRepositoryImpl
import com.ougi.chatlistscreenimpl.domain.repository.ChatListRepository
import com.ougi.chatlistscreenimpl.domain.repository.MessagingConnectionStateRepository
import com.ougi.chatlistscreenimpl.domain.usecase.FetchChatListUseCase
import com.ougi.chatlistscreenimpl.domain.usecase.FetchChatListUseCaseImpl
import com.ougi.chatlistscreenimpl.domain.usecase.ObserveConnectionUseCase
import com.ougi.chatlistscreenimpl.domain.usecase.ObserveConnectionUseCaseImpl
import com.ougi.chatlistscreenimpl.presentation.viewmodel.ChatListFragmentViewModel
import com.ougi.chatlistscreenimpl.presentation.viewmodel.ChatListFragmentViewModelImpl
import com.ougi.chatlistscreenimpl.presentation.viewmodel.ChatListToolbarFragmentViewModel
import com.ougi.chatlistscreenimpl.presentation.viewmodel.ChatListToolbarFragmentViewModelImpl
import com.ougi.coreutils.dagger.Feature
import dagger.Binds
import dagger.Module

@Module
interface ChatListScreenModule {

    @[Feature Binds]
    fun bindChatListRepository(chatListRepositoryImpl: ChatListRepositoryImpl): ChatListRepository

    @[Feature Binds]
    fun bindFetchChatListUseCase(fetchChatListUseCaseImpl: FetchChatListUseCaseImpl): FetchChatListUseCase

    @[Feature Binds]
    fun bindChatListViewModelFactory(factory: ChatListFragmentViewModelImpl.Factory): ChatListFragmentViewModel.Factory


    @[Feature Binds]
    fun bindMessagingConnectionStateRepository(messagingConnectionStateRepositoryImpl: MessagingConnectionStateRepositoryImpl): MessagingConnectionStateRepository

    @[Feature Binds]
    fun bindObserveConnectionUseCase(observeConnectionUseCaseImpl: ObserveConnectionUseCaseImpl): ObserveConnectionUseCase

    @[Feature Binds]
    fun bindChatListToolbarFragmentViewModelFactory(factory: ChatListToolbarFragmentViewModelImpl.Factory): ChatListToolbarFragmentViewModel.Factory
}