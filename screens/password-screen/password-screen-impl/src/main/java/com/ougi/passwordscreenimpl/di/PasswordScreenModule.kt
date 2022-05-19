package com.ougi.passwordscreenimpl.di

import com.ougi.coreutils.dagger.Feature
import com.ougi.passwordscreenapi.data.PasswordScreenStarter
import com.ougi.passwordscreenimpl.PasswordScreenStarterImpl
import com.ougi.passwordscreenimpl.data.repository.CreatePasswordRepositoryImpl
import com.ougi.passwordscreenimpl.data.repository.EnterPasswordRepositoryImpl
import com.ougi.passwordscreenimpl.domain.repository.CreatePasswordRepository
import com.ougi.passwordscreenimpl.domain.repository.EnterPasswordRepository
import com.ougi.passwordscreenimpl.domain.usecase.*
import com.ougi.passwordscreenimpl.presentation.viewmodel.*
import dagger.Binds
import dagger.Module

@Module
interface PasswordScreenModule {

    @[Feature Binds]
    fun bindCreatePasswordRepository(createPasswordRepositoryImpl: CreatePasswordRepositoryImpl): CreatePasswordRepository

    @[Feature Binds]
    fun bindCreatePasswordUseCase(createPasswordUseCaseImpl: CreatePasswordUseCaseImpl): CreatePasswordUseCase

    @[Feature Binds]
    fun bindCreatePasswordViewModelFactory(factory: CreatePasswordFragmentViewModelImpl.Factory): CreatePasswordFragmentViewModel.Factory


    @[Feature Binds]
    fun bindEnterPasswordRepository(enterPasswordRepositoryImpl: EnterPasswordRepositoryImpl): EnterPasswordRepository

    @[Feature Binds]
    fun bindEnterPasswordUseCase(enterPasswordUseCaseImpl: EnterPasswordUseCaseImpl): EnterPasswordUseCase

    @[Feature Binds]
    fun bindEnterPasswordViewModelFactory(factory: EnterPasswordFragmentViewModelImpl.Factory): EnterPasswordFragmentViewModel.Factory

    @[Feature Binds]
    fun bindPasswordScreenStarter(passwordScreenStarterImpl: PasswordScreenStarterImpl): PasswordScreenStarter


    @[Feature Binds]
    fun bindUserRegistrationUseCase(userRegistrationUseCaseImpl: UserRegistrationUseCaseImpl): UserRegistrationUseCase

    @[Feature Binds]
    fun bindUserRegistrationViewModelFactory(factory: UserRegistrationViewModelImpl.Factory): UserRegistrationViewModel.Factory
}