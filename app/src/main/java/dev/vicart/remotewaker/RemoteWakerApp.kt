package dev.vicart.remotewaker

import android.app.Application
import com.google.android.material.color.DynamicColors

class RemoteWakerApp : Application() {

    override fun onCreate() {
        super.onCreate()
        DynamicColors.applyToActivitiesIfAvailable(this)
    }
}