package hr.ferit.zvonimirpavlovic.taskie

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import hr.ferit.zvonimirpavlovic.taskie.common.PREFERENCES_NAME
import hr.ferit.zvonimirpavlovic.taskie.di.interactorModule
import hr.ferit.zvonimirpavlovic.taskie.di.networkingModule
import hr.ferit.zvonimirpavlovic.taskie.di.presentationModule
import hr.ferit.zvonimirpavlovic.taskie.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Taskie : Application() {

    companion object {
        lateinit var instance: Taskie
            private set

        fun getAppContext(): Context = instance.applicationContext
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(listOf(interactorModule, networkingModule, presentationModule, repositoryModule))
            androidContext(this@Taskie)}
        instance = this
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

    }

    fun providePreferences(): SharedPreferences = instance.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
}