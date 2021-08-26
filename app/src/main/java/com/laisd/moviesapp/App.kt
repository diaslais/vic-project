package com.laisd.moviesapp

import android.app.Application
import com.laisd.moviesapp.data.di.dataBaseModules
import com.laisd.moviesapp.data.di.dataModules
import com.laisd.moviesapp.data.di.networkModules
import com.laisd.moviesapp.domain.di.domainModules
import com.laisd.moviesapp.presentation.di.presentationModules
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