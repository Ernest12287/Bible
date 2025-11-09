package com.ernest.bible

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

data class BookViewState(
    val isLoading: Boolean = true,
    val oldTestament: List<String> = emptyList(),
    val newTestament: List<String> = emptyList(),
    val error: String? = null
)

class BibleViewModel(application: Application) : AndroidViewModel(application) {

    private val database = BibleDatabase.getDatabase(application)
    val dao = database.verseDao()

    var uiState by mutableStateOf(BookViewState())
        private set

    init {
        loadBookNames()
    }

    private fun loadBookNames() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, error = null)

            dao.getBookNames().collect { allBooks ->
                if (allBooks.isEmpty()) {
                    uiState = uiState.copy(isLoading = true)
                } else {
                    val oldTestament = allBooks.take(39)
                    val newTestament = allBooks.drop(39)

                    uiState = uiState.copy(
                        isLoading = false,
                        oldTestament = oldTestament,
                        newTestament = newTestament
                    )
                }
            }
        }
    }

    companion object {
        fun Factory(application: Application): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    if (modelClass.isAssignableFrom(BibleViewModel::class.java)) {
                        return BibleViewModel(application) as T
                    }
                    throw IllegalArgumentException("Unknown ViewModel class")
                }
            }
    }
}