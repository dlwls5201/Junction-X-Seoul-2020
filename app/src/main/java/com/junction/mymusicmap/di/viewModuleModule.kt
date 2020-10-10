package com.junction.mymusicmap.di

import com.junction.mymusicmap.presentation.login.LoginViewModel
import com.junction.mymusicmap.presentation.main.MainViewModel
import com.junction.mymusicmap.presentation.userpage.UserPageViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModuleModule = module {

    viewModel { LoginViewModel() }

    viewModel { MainViewModel() }

    viewModel { UserPageViewModel(get()) }
}