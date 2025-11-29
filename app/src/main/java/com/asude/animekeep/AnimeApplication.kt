package com.asude.animekeep

import android.app.Application
import com.asude.animekeep.data.AppContainer
import com.asude.animekeep.data.DefaultAppContainer

class AnimeApplication : Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
    }
}