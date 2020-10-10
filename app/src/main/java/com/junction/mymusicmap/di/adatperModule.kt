package com.junction.mymusicmap.di

import com.junction.mymusicmap.presentation.userpage.UserAdapter
import org.koin.dsl.module

val adapterModule = module {
    factory {
        UserAdapter(items = ArrayList())
    }
}