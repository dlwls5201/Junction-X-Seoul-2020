package com.junction.mymusicmap.di

import com.junction.mymusicmap.data.api.YoutubeMp32Api
import com.junction.mymusicmap.data.api.YoutubeSearchApi
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {

    single(named("search")) {
        get<Retrofit>(named("retrofit-search")).create(YoutubeSearchApi::class.java)
    }

    single(named("mp32")) {
        get<Retrofit>(named("retrofit-mp32")).create(YoutubeMp32Api::class.java)
    }
}
