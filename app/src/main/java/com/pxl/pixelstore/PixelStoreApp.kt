package com.pxl.pixelstore

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PixelStoreApp : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}