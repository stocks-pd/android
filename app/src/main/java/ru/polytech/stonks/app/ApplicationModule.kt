package ru.polytech.stonks.app

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import ru.polytech.stonks.data.util.defaultPrefs
import javax.inject.Singleton

@Module
class ApplicationModule {

    @Singleton
    @Provides
    fun provideSharedPreferences(context: Context): SharedPreferences = defaultPrefs(context)

}