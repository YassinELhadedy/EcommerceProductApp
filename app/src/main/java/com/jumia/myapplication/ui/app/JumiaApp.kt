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
           this, "722828e5-c9f7-4cbf-8889-6dbd6218ad49",
            Analytics::class.java, Crashes::class.java
        )
    }
}