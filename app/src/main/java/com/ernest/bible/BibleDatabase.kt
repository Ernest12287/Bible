package com.ernest.bible

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Verse::class], version = 1, exportSchema = false)
abstract class BibleDatabase : RoomDatabase() {

    abstract fun verseDao(): VerseDao

    companion object {
        @Volatile
        private var INSTANCE: BibleDatabase? = null

        fun getDatabase(context: Context): BibleDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BibleDatabase::class.java,
                    "bible_database"
                )
                    // Copy the pre-built database from assets
                    .createFromAsset("databases/bible_database.db")
                    // Fallback: if the database file doesn't exist, create empty
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}