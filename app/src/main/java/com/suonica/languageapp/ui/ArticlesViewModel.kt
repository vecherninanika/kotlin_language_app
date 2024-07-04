package com.suonica.languageapp.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface ArticlesUiState {
    data class Success(val articles: List<DBArticle>) : ArticlesUiState

    object Error : ArticlesUiState

    object Loading : ArticlesUiState
}

class ArticlesViewModel : ViewModel() {
    var articlesUiState: ArticlesUiState by mutableStateOf(ArticlesUiState.Loading)
        private set

    init {
        getArticlesInModel()
    }

    fun getArticlesInModel() {
        viewModelScope.launch {
            articlesUiState = ArticlesUiState.Loading
            articlesUiState =
                try {
                    val listResult = getArticles()
                    ArticlesUiState.Success(
                        listResult,
                    )
                } catch (e: IOException) {
                    Log.e("ArticleViewModel", e.message, e)
                    ArticlesUiState.Error
                } catch (e: RuntimeException) {
                    Log.e("ArticleViewModel", e.message, e)
                    ArticlesUiState.Error
                }
        }
    }
}
