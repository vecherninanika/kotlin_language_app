package com.suonica.languageapp.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.suonica.language_app.R

@Composable
fun AddWordScreen(
    modifier: Modifier = Modifier,
    onBackClicked: () -> Unit,
    onSaveClicked: (word: String, translation: String) -> Unit,
    userId: String,
) {
    var word by remember { mutableStateOf("") }
    var translation by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier =
        modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        OutlinedTextField(
            value = word,
            onValueChange = { word = it },
            label = { Text(stringResource(R.string.word)) },
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = translation,
            onValueChange = { translation = it },
            label = { Text(stringResource(R.string.translation)) },
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
        ) {
            Button(onClick = onBackClicked) {
                Text(stringResource(R.string.back))
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = {
                addWord(userId, word, translation, callback = { text ->
                    Toast.makeText(
                        context,
                        text,
                        Toast.LENGTH_LONG,
                    ).show()
                })
                onSaveClicked(word, translation)
            }) {
                Text(stringResource(R.string.save))
            }
        }
    }
}
