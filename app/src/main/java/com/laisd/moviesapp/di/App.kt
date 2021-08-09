package com.laisd.moviesapp.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(networkModules)
            modules(dataBaseModules)
            modules(dataModules)
            modules(domainModules)
            modules(presentationModules)
        }
    }
}