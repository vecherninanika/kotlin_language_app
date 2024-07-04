package com.suonica.languageapp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.suonica.languageapp.R

@Composable
fun WordItem(
    original: String,
    translated: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier =
                Modifier
                    .weight(1f)
                    .padding(start = 16.dp),
            text = "$original - $translated",
        )
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
        )
    }
}

@Composable
fun WordItem(
    original: String,
    translated: String,
    learnt: Boolean,
    modifier: Modifier = Modifier,
    onCheckedChange: (Boolean) -> Unit,
) {
    var checkedState by rememberSaveable { mutableStateOf(learnt) }

    WordItem(
        original = original,
        translated = translated,
        checked = checkedState,
        onCheckedChange = { newValue ->
            checkedState = newValue
            onCheckedChange(newValue)
        },
        modifier = modifier,
    )
}

@Composable
fun WordsList(
    modifier: Modifier = Modifier,
    words: List<DBWord>,
    userId: String,
) {
    LazyColumn(modifier = modifier) {
        items(
            items = words,
            key = { word -> word.original },
        ) { word ->
            WordItem(
                original = word.original,
                translated = word.translated,
                learnt = word.learnt,
                onCheckedChange = {
                    val newWord = DBWord(word.id, it, word.original, word.translated)
                    updateWord(userId, newWord)
                },
            )
        }
    }
}

@Composable
fun WordsScreen(
    modifier: Modifier = Modifier,
    onArticlesButtonClicked: () -> Unit,
    onAddWordClicked: () -> Unit,
    userId: String,
) {
    Column(modifier = modifier.padding(16.dp)) {
        Text("Dictionary")
        Button(onClick = onAddWordClicked, Modifier.padding(top = 8.dp)) {
            Text("Add a word")
        }
        val wordsViewModel: WordsViewModel = viewModel()
        WordsListScreen(wordsViewModel.wordsUiState, userId = userId)
    }
    NavigationMenu(onArticlesButtonClicked)
}

@Composable
fun WordsListScreen(
    wordsUiState: WordsUiState,
    userId: String,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    when (wordsUiState) {
        is WordsUiState.Loading -> Text("Loading")
        is WordsUiState.Success ->
            WordsList(
                words = wordsUiState.words,
                userId = userId,
            )
        is WordsUiState.Error -> Text("Error")
    }
}

@Composable
private fun NavigationMenu(onArticlesButtonClicked: () -> Unit) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_medium)),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
        verticalAlignment = Alignment.Bottom,
    ) {
        OutlinedButton(
            modifier = Modifier.weight(1f),
            onClick = {},
            enabled = false,
        ) {
            Text(stringResource(R.string.dictionary))
        }
        Button(
            modifier = Modifier.weight(1f),
            onClick = onArticlesButtonClicked,
        ) {
            Text(stringResource(R.string.articles))
        }
    }
}
