package com.suonica.languageapp.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.io.IOException

/**
 * UI state for the Home screen
 */
sealed interface WordsUiState {
    data class Success(val words: List<DBWord>) : WordsUiState

    object Error : WordsUiState

    object Loading : WordsUiState
}

class WordsViewModel : ViewModel() {
    /** The mutable State that stores the status of the most recent request */
    var wordsUiState: WordsUiState by mutableStateOf(WordsUiState.Loading)
        private set

    init {
        getWords(USER_ID)
    }

    fun getWords(userId: String) {
        viewModelScope.launch {
            wordsUiState = WordsUiState.Loading
            wordsUiState =
                try {
                    val listResult = getWordsForUser(userId)
                    WordsUiState.Success(
                        listResult,
                    )
                } catch (e: IOException) {
                    WordsUiState.Error
                } catch (e: RuntimeException) {
                    WordsUiState.Error
                }
        }
    }
}
