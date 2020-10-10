package com.junction.mymusicmap.di

import com.junction.mymusicmap.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {

    val mp32Url = "https://youtube-to-mp32.p.rapidapi.com"

    single(named("retrofit-mp32")) {
        Retrofit.Builder()
            .baseUrl(mp32Url)
            .client(get(named("client-mp32")))
            .addCallAdapterFactory(get())
            .addConverterFactory(get())
            .build()
    }

    single(named("client-mp32")) {
        OkHttpClient.Builder().apply {

            //TimeOut 시간을 지정합니다.
            readTimeout(60, TimeUnit.SECONDS)
            connectTimeout(60, TimeUnit.SECONDS)
            writeTimeout(5, TimeUnit.SECONDS)

            // 이 클라이언트를 통해 오고 가는 네트워크 요청/응답을 로그로 표시하도록 합니다.
            addInterceptor(get(named("logger")))

            // header 에 정보를 추가해 준다.
            addInterceptor(get(named("header-mp32")))
        }.build()
    }

    single(named("header-mp32")) {
        Interceptor { chain ->
            chain.run {
                proceed(
                    request()
                        .newBuilder()
                        .addHeader("x-rapidapi-host", "youtube-to-mp32.p.rapidapi.com")
                        .addHeader("x-rapidapi-key", "7d663afb3fmsh590bcfcede68795p19dc63jsn95cdec1c2d0b")
                        .build()
                )
            }
        }
    }



    val searchUrl = "https://youtube-search-results.p.rapidapi.com/"

    single(named("retrofit-search")) {
        Retrofit.Builder()
            .baseUrl(searchUrl)
            .client(get(named("client-search")))
            .addCallAdapterFactory(get())
            .addConverterFactory(get())
            .build()
    }

    single(named("client-search")) {
        OkHttpClient.Builder().apply {

            //TimeOut 시간을 지정합니다.
            readTimeout(60, TimeUnit.SECONDS)
            connectTimeout(60, TimeUnit.SECONDS)
            writeTimeout(5, TimeUnit.SECONDS)

            // 이 클라이언트를 통해 오고 가는 네트워크 요청/응답을 로그로 표시하도록 합니다.
            addInterceptor(get(named("logger")))

            // header 에 정보를 추가해 준다.
            addInterceptor(get(named("header-search")))
        }.build()
    }

    single(named("header-search")) {
        Interceptor { chain ->
            chain.run {
                proceed(
                    request()
                        .newBuilder()
                        .addHeader("x-rapidapi-host", "youtube-search-results.p.rapidapi.com")
                        .addHeader("x-rapidapi-key", "7d663afb3fmsh590bcfcede68795p19dc63jsn95cdec1c2d0b")
                        .build()
                )
            }
        }
    }

    /**
     * common
     */
    single<Interceptor>(named("logger")) {
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    single<CallAdapter.Factory> {
        RxJava2CallAdapterFactory.createAsync()
    }

    single<Converter.Factory> {
        GsonConverterFactory.create()
    }
}