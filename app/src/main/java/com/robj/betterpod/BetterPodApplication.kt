package com.robj.betterpod

import android.app.Application
import com.robj.betterpod.di.appModule
import com.robj.betterpod.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext
import org.koin.core.logger.Level

class BetterPodApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        GlobalContext.startKoin {
            androidLogger(Level.NONE)
            androidContext(this@BetterPodApplication)
            modules(repositoryModule, appModule)
        }
    }
}