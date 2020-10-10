package com.junction.mymusicmap.di

import com.junction.mymusicmap.presentation.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModuleModule = module {

    viewModel { LoginViewModel() }

}