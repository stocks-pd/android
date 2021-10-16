package ru.polytech.stonks.app

lateinit var appComponent: ApplicationComponent

fun App.initDagger() {
    appComponent = DaggerApplicationComponent
        .builder()
        .appContext(this)
        .build()

    appComponent.inject(this)
}