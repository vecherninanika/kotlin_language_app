package com.suonica.language_app.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.suonica.language_app.ui.theme.AppTheme


enum class LanguageAppScreen() {
    Login,
    Articles,
    Dictionary
}


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                MyApp(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
fun MyApp(modifier: Modifier = Modifier) {
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }

    Surface(modifier, color = MaterialTheme.colorScheme.background) {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = LanguageAppScreen.Login.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = LanguageAppScreen.Login.name) {

            }
        }

        if (shouldShowOnboarding) {
            Login(onContinueClicked = { shouldShowOnboarding = false })
        } else {
            Articles()
        }
    }
}


@Preview
@Composable
fun MyAppPreview() {
    AppTheme {
        MyApp(Modifier.fillMaxSize())
    }
}
