package com.example.immunify

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import org.osmdroid.config.Configuration

@HiltAndroidApp
class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        // OSMDroid needs a user agent value
        Configuration.getInstance().userAgentValue = packageName
    }
}