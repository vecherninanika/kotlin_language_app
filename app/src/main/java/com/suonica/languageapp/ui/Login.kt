package com.suonica.languageapp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.suonica.language_app.R

@Composable
fun Login(
    onContinueClicked: () -> Unit,
    modifier: Modifier = Modifier,
    isDarkTheme: Boolean,
    onThemeToggleClick: () -> Unit
) {

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(onClick = onThemeToggleClick) {
            Text(if (isDarkTheme) stringResource(R.string.light_theme) else stringResource(R.string.dark_theme))
        }
        Text(stringResource(R.string.welcome))
        Button(
            modifier = Modifier.padding(vertical = 24.dp),
            onClick = onContinueClicked,
        ) {
            Text(stringResource(R.string.login))
        }
    }
}
