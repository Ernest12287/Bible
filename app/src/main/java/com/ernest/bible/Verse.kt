package com.ernest.bible

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Index

@Entity(
    tableName = "verses",
    indices = [
        Index(value = ["bookName"], name = "idx_book"),
        Index(value = ["bookName", "chapterNumber"], name = "idx_book_chapter")
    ]
)
data class Verse(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,

    val bookName: String,
    val chapterNumber: Int,
    val verseNumber: Int,
    val text: String
)