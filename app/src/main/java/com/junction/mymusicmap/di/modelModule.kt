package com.junction.mymusicmap.di

import com.junction.mymusicmap.presentation.userpage.ZepetoModel
import com.junction.mymusicmap.presentation.userpage.ZepetoModelImpl
import org.koin.dsl.module

val modelModule = module {
    factory<ZepetoModel> {
        ZepetoModelImpl(get())
    }
}