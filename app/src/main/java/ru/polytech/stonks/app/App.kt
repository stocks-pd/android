package ru.polytech.stonks.app

import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        initDagger()
    }
}