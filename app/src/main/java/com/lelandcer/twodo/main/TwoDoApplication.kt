package com.lelandcer.twodo.main

import android.app.Application
import androidx.room.Room
import com.lelandcer.twodo.database.AppDatabase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TwoDoApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        buildDatabase()

    }

    private fun buildDatabase() {
        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name"
        ).build()

    }


    companion object {
        public lateinit var db: AppDatabase
    }
}