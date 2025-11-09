package com.ernest.bible

import com.google.gson.annotations.SerializedName

// 1. Matches the structure of the smallest unit: a single verse inside a chapter
data class JsonVerse(
    @SerializedName("verse")
    val verseNumber: String, // Note: it's a String in JSON, we'll convert it to Int later

    @SerializedName("text")
    val text: String
)

// 2. Matches the structure of a chapter block in the JSON
data class JsonChapter(
    @SerializedName("chapter")
    val chapterNumber: String, // Note: it's a String in JSON

    @SerializedName("verses")
    val verses: List<JsonVerse>
)

// 3. Matches the top-level structure of the 'Genesis.json' file
data class BookJson(
    @SerializedName("book")
    val book: String,

    @SerializedName("chapters")
    val chapters: List<JsonChapter>
)

// 4. This model is for books.json (just a list of strings)
data class BookList(
    val bookNames: List<String>
)