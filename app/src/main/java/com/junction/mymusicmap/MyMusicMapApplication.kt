package com.junction.mymusicmap

import android.app.Application
import com.junction.mymusicmap.di.apiModule
import com.junction.mymusicmap.di.modelModule
import com.junction.mymusicmap.di.networkModule
import com.junction.mymusicmap.di.viewModuleModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.logger.AndroidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.EmptyLogger

class MyMusicMapApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        setupKoin()
    }

    private fun setupKoin() {
        startKoin {
            logger(
                if (BuildConfig.DEBUG) {
                    AndroidLogger()
                } else {
                    EmptyLogger()
                }
            )

            androidContext(this@MyMusicMapApplication)

            modules(
                listOf(
                    viewModuleModule,
                    networkModule,
                    apiModule,
                    modelModule
                )
            )
        }
    }
}