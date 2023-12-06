package com.example.groupdata

import android.content.Context
import androidx.room.Room

object Dependencies {

    private lateinit var applicationContext: Context

    val server = ServerConnection()


    fun init(context: Context) {
        applicationContext = context
    }

    private val appDatabase: AppDataBase by lazy {
        Room.databaseBuilder(applicationContext, AppDataBase::class.java, "mydatabase.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    val repository: Repository by lazy { Repository(appDatabase.getDao, server) }

    val Const = Const()
}