package com.ernest.bible

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface VerseDao {

    @Query("SELECT * FROM verses WHERE bookName = :book AND chapterNumber = :chapter ORDER BY verseNumber ASC")
    fun getChapter(book: String, chapter: Int): Flow<List<Verse>>

    @Query("SELECT DISTINCT bookName FROM verses ORDER BY id ASC")
    fun getBookNames(): Flow<List<String>>

    @Query("SELECT MAX(chapterNumber) FROM verses WHERE bookName = :book")
    suspend fun getChapterCount(book: String): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(verse: List<Verse>)

}