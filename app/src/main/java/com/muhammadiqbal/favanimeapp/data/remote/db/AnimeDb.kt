package com.muhammadiqbal.favanimeapp.data.remote.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [AnimeEntity::class], version = 1, exportSchema = false)
abstract class AnimeDb: RoomDatabase() {
    abstract val dao:AnimeDao

    companion object {
        @Volatile
        var INSTANCE: AnimeDb? = null

        fun getInstace(context: Context): AnimeDb {
            synchronized(this) {
                var instance = INSTANCE

                if (instance === null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AnimeDb::class.java,
                        "bmi.db"
                    ).fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}