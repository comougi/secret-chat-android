package com.ougi.passwordscreenimpl.di

import com.ougi.coreutils.dagger.Feature
import com.ougi.passwordscreenapi.data.PasswordScreenStarter
import com.ougi.passwordscreenimpl.PasswordScreenStarterImpl
import com.ougi.passwordscreenimpl.data.repository.PasswordRepositoryImpl
import com.ougi.passwordscreenimpl.domain.repository.PasswordRepository
import com.ougi.passwordscreenimpl.domain.usecase.*
import com.ougi.passwordscreenimpl.presentation.viewmodel.*
import dagger.Binds
import dagger.Module

@Module
interface PasswordScreenModule {

    @[Feature Binds]
    fun bindPasswordRepository(passwordRepositoryImpl: PasswordRepositoryImpl): PasswordRepository


    @[Feature Binds]
    fun bindCheckHasPasswordUseCase(checkHasPasswordUseCaseImpl: CheckHasPasswordUseCaseImpl): CheckHasPasswordUseCase

    @[Feature Binds]
    fun bindPasswordActivityViewModelFactory(factory: PasswordScreenActivityViewModelImpl.Factory): PasswordScreenActivityViewModel.Factory


    @[Feature Binds]
    fun bindCreatePasswordUseCase(createPasswordUseCaseImpl: CreatePasswordUseCaseImpl): CreatePasswordUseCase

    @[Feature Binds]
    fun bindCreatePasswordViewModelFactory(factory: CreatePasswordFragmentViewModelImpl.Factory): CreatePasswordFragmentViewModel.Factory


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