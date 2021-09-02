package com.jumia.myapplication.ui.app

import android.app.Application
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class JumiaApp(): Application(){
    override fun onCreate() {
        super.onCreate()
        AppCenter.start(
           this, "4accee15-1036-41cb-ac1f-05f44c30ae07",
            Analytics::class.java, Crashes::class.java
        )
    }
}