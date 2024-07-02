package com.suonica.language_app.ui

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suonica.language_app.R
import com.suonica.language_app.ui.theme.AppTheme

// TODO не показывает статьи

@Composable
fun Articles(
    modifier: Modifier = Modifier,
) {
    var articles by remember { mutableStateOf(listOf<DBArticle>()) }

    LaunchedEffect(Unit) {
        val articlesList = mutableListOf<DBArticle>()
        getArticles() {
            article -> articlesList.add(article)
        }
        articles = articlesList
    }

    LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
        items(items = articles) { article ->
            Article(articleTitle = article.title, articleText = article.text)
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_medium)),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
        verticalAlignment = Alignment.Bottom
    ) {
        Button(
            modifier = Modifier.weight(1f),
            onClick = {}
        ) {
            Text(stringResource(R.string.dictionary))
        }
        OutlinedButton(
            modifier = Modifier.weight(1f),
            onClick = {},
            enabled = false
        ) {
            Text(stringResource(R.string.articles))
        }
    }
}

@Composable
private fun Article(articleTitle: String, articleText: String, modifier: Modifier = Modifier) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        modifier = modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        CardContent(articleTitle, articleText)
    }
}

@Composable
private fun CardContent(articleTitle: String, articleText: String) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .padding(12.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(12.dp)
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
                contentDescription = if (expanded) {
                    stringResource(R.string.show_less)
                } else {
                    stringResource(R.string.show_more)
                }
            )
        }
    }
}

@Preview(
    showBackground = true,
    widthDp = 320,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "ArticlePreviewDark"
)
@Preview(showBackground = true, widthDp = 320)
@Composable
fun ArticlePreview() {
    AppTheme {
        Articles()
    }
}
