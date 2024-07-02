package com.suonica.language_app.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.material3.Checkbox
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.suonica.language_app.R

@Composable
fun WordItem(
    original: String,
    translated: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier, verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp),
            text = "$original - $translated"
        )
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
        IconButton(onClick = onClose) {
            Icon(Icons.Filled.Close, contentDescription = "Close")
        }
    }
}

@Composable
fun WordItem(original: String, translated: String, onClose: () -> Unit, modifier: Modifier = Modifier) {
    var checkedState by rememberSaveable { mutableStateOf(false) }

    WordItem(
        original = original,
        translated = translated,
        checked = checkedState,
        onCheckedChange = { newValue -> checkedState = newValue },
        onClose = onClose,
        modifier = modifier,
    )
}

@Composable
fun Dictionary(modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
        Text("Dictionary")
        Button(onClick = {  }, Modifier.padding(top = 8.dp)) {
            Text("Add a word")
        }
    }
}

@Composable
fun WordsList(
    modifier: Modifier = Modifier,
    map: Map<String, String>,
    onCloseWord: (String) -> Unit,
) {
    LazyColumn(modifier = modifier) {
        items(
            items = map.entries.toList(),
            key = { (key, _) -> key }
        ) { (key, value) ->
            WordItem(
                original = key,
                translated = value,
                onClose = { onCloseWord(key) }
            )
        }
    }
}

@Composable
fun WordsScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        val map = remember { getWordsForUser(getUser(username) { user -> user.username}.toString()).toMutableStateList() }
        WordsList(
            map = map,
            onCloseWord = { original: String -> map.remove(original) }
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_medium)),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
        verticalAlignment = Alignment.Bottom
    ) {
        OutlinedButton(
            modifier = Modifier.weight(1f),
            onClick = {},
            enabled = false
        ) {
            Text(stringResource(R.string.dictionary))
        }
        Button(
            modifier = Modifier.weight(1f),
            onClick = {}
        ) {
            Text(stringResource(R.string.articles))
        }
    }
}
