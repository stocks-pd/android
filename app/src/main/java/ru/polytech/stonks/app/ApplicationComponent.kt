package ru.polytech.stonks.app

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.polytech.stonks.presentation.feathers.catalog.CatalogFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [
    ApplicationModule::class,
])
interface ApplicationComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun appContext(context: Context): Builder

        fun build(): ApplicationComponent
    }

    fun inject(application: App)

    fun inject(fragment: CatalogFragment)

    fun appContext(): Context
}