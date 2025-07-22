package com.pxl.pixelstore.di

import android.content.Context
import com.pxl.pixelstore.data.db.DatabaseDriverFactory
import com.pxl.pixelstore.data.db.PasswordDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DiModule {
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): PasswordDatabase {
        val factory = DatabaseDriverFactory(context)
        return PasswordDatabase(factory)
    }
}