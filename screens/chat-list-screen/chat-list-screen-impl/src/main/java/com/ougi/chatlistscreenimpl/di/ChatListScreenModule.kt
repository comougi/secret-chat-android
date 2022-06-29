package com.ougi.chatlistscreenimpl.di

import com.ougi.chatlistscreenimpl.domain.usecase.*
import com.ougi.chatlistscreenimpl.presentation.viewmodel.*
import com.ougi.coreutils.dagger.Feature
import dagger.Binds
import dagger.Module

@Module
interface ChatListScreenModule {

    @[Feature Binds]
    fun bindGetChatListUseCase(getChatListUseCaseImpl: GetChatListUseCaseImpl): GetChatListUseCase

    @[Feature Binds]
    fun bindChatListViewModelFactory(factory: ChatListFragmentViewModelImpl.Factory): ChatListFragmentViewModel.Factory


    @[Feature Binds]
    fun bindObserveConnectionUseCase(observeConnectionUseCaseImpl: ObserveConnectionUseCaseImpl): ObserveConnectionUseCase

    @[Feature Binds]
    fun bindChatListToolbarFragmentViewModelFactory(factory: ChatListToolbarFragmentViewModelImpl.Factory): ChatListToolbarFragmentViewModel.Factory

    @[Feature Binds]
    fun bindCreateChatUseCase(createChatUseCaseImpl: CreateChatUseCaseImpl): CreateChatUseCase

    @[Feature Binds]
    fun bindCreateChatDialogFragmentViewModelFactory(factory: CreateChatDialogFragmentViewModelImpl.Factory): CreateChatDialogFragmentViewModel.Factory
}