package com.suonica.languageapp.ui

import android.content.res.Configuration
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.suonica.language_app.R
import com.suonica.languageapp.ui.theme.AppTheme

@Composable
fun Articles(
    modifier: Modifier = Modifier,
    onDictionaryButtonClicked: () -> Unit = {},
    isDarkTheme: Boolean,
    onThemeToggleClick: () -> Unit
) {
    Column(modifier = modifier.padding(vertical = 4.dp)) {
        Button(onClick = onThemeToggleClick) {
            Text(if (isDarkTheme) stringResource(R.string.light_theme) else stringResource(R.string.dark_theme))
        }
        val articlesViewModel: ArticlesViewModel = viewModel()
        ArticlesListScreen(articlesUiState = articlesViewModel.articlesUiState)
    }

    Row(
        modifier =
        Modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_medium)),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
        verticalAlignment = Alignment.Bottom,
    ) {
        Button(
            modifier = Modifier.weight(1f),
            onClick = onDictionaryButtonClicked,
        ) {
            Text(stringResource(R.string.dictionary))
        }
        OutlinedButton(
            modifier = Modifier.weight(1f),
            onClick = {},
            enabled = false,
        ) {
            Text(stringResource(R.string.articles))
        }
    }
}

@Composable
fun ArticlesList(
    modifier: Modifier = Modifier,
    articles: List<DBArticle>,
) {
    LazyColumn(modifier = modifier) {
        items(items = articles) { article ->
            Article(articleTitle = article.title, articleText = article.text)
        }
    }
}

@Composable
fun ArticlesListScreen(
    articlesUiState: ArticlesUiState,
    modifier: Modifier = Modifier,
) {
    when (articlesUiState) {
        is ArticlesUiState.Loading -> Text(stringResource(R.string.loading))
        is ArticlesUiState.Success -> ArticlesList(articles = articlesUiState.articles)
        is ArticlesUiState.Error -> Text(stringResource(R.string.error))
    }
}

@Composable
private fun Article(
    articleTitle: String,
    articleText: String,
    modifier: Modifier = Modifier,
) {
    Card(
        colors =
            CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primary,
            ),
        modifier = modifier.padding(vertical = 4.dp, horizontal = 8.dp),
    ) {
        CardContent(articleTitle, articleText)
    }
}

@Composable
private fun CardContent(
    articleTitle: String,
    articleText: String,
) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    Row(
        modifier =
        Modifier
            .padding(12.dp)
            .animateContentSize(
                animationSpec =
                spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow,
                ),
            ),
    ) {
        Column(
            modifier =
            Modifier
                .weight(1f)
                .padding(12.dp),
        ) {
            Text(text = articleTitle)
            if (expanded) {
                Text(
                    text = articleText,
                )
            }
        }
        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                contentDescription =
                    if (expanded) {
                        stringResource(R.string.show_less)
                    } else {
                        stringResource(R.string.show_more)
                    },
            )
        }
    }
}
