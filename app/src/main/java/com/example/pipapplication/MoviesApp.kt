package com.example.pipapplication

import android.app.Application
import com.example.pipapplication.di.ApplicationComponent
import com.example.pipapplication.di.DaggerApplicationComponent
import com.example.pipapplication.utils.PreferenceManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class MoviesApp:Application() {
    private val applicationScope = CoroutineScope(SupervisorJob())
    lateinit var applicationComponent: ApplicationComponent
    override fun onCreate() {
        super.onCreate()
//        applicationComponent=DaggerApplicationComponent.builder().build()
        applicationComponent=DaggerApplicationComponent.factory().create(this)
        PreferenceManager.init(this)
    }
}