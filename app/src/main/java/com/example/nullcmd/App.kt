package com.example.nullcmd

import android.app.Application
import com.example.nullcmd.di.apiModule
import com.example.nullcmd.di.networkModule
import com.example.nullcmd.di.serviceModule
import com.example.nullcmd.di.viewModelModule
import org.koin.core.context.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(networkModule, apiModule, viewModelModule, serviceModule)
        }
    }
}