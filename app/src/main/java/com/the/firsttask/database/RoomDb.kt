package com.the.firsttask.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MovieEntity::class], version = 1)
abstract class RoomDb : RoomDatabase() {
    abstract fun movieDao(): MovieDao?

    companion object {
        private var INSTANCE: RoomDb? = null

        fun getAppDatabase(context: Context): RoomDb? {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder<RoomDb>(
                    context.applicationContext,
                    RoomDb::class.java,
                    "MovieDB"
                ).allowMainThreadQueries().build()

            }
            return INSTANCE
        }

    }
}