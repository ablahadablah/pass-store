package com.pxl.pixelstore

import android.app.Application
import androidx.compose.runtime.Composer
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PixelStoreApp : Application() {
    override fun onCreate() {
        super.onCreate()

//        Composer.setDiagnosticStackTraceEnabled(BuildConfig.DEBUG)

        startWorkers()

    }

    private fun startWorkers() {
//        val syncWorkRequest = PeriodicWorkRequestBuilder<SyncWorker>(Duration.ofMinutes(20)).build()
//        val purgeWorkRequest = PeriodicWorkRequestBuilder<PurgeWorker>(Duration.ofDays(1)).build()
//
//        WorkManager.getInstance(applicationContext)
//            .enqueue(syncWorkRequest)
//
//        WorkManager.getInstance(applicationContext)
//            .enqueue(purgeWorkRequest)
    }
}