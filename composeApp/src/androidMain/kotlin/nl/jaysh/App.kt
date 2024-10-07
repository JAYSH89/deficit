package nl.jaysh

import android.app.Application
import core.di.KoinInitializer
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        Napier.base(DebugAntilog())
        KoinInitializer(applicationContext).init()
    }
}