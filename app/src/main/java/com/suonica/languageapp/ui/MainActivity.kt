package com.suonica.languageapp.ui

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
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
import com.suonica.languageapp.ui.theme.AppTheme

enum class LanguageAppScreen() {
    Login,
    Articles,
    Dictionary,
    AddWord,
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

const val USER_ID = "6DIiyqQ8Gs276IXPlkne"

@Composable
fun MyApp(modifier: Modifier = Modifier) {
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }
    val userId = USER_ID

    Surface(modifier, color = MaterialTheme.colorScheme.background) {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = LanguageAppScreen.Dictionary.name,
        ) {
            composable(route = LanguageAppScreen.Login.name) {
                Login(
                    onContinueClicked = { shouldShowOnboarding = false },
                    modifier = Modifier.fillMaxHeight(),
                )
            }
            composable(route = LanguageAppScreen.Articles.name) {
                Articles(
                    modifier = Modifier.fillMaxHeight(),
                    onDictionaryButtonClicked = { navController.navigate(LanguageAppScreen.Dictionary.name) },
                )
            }
            composable(route = LanguageAppScreen.Dictionary.name) {
                WordsScreen(
                    modifier = Modifier.fillMaxHeight(),
                    onArticlesButtonClicked = { navController.navigate(LanguageAppScreen.Articles.name) },
                    onAddWordClicked = { navController.navigate(LanguageAppScreen.AddWord.name) },
                    userId = userId,
                )
            }
            composable(route = LanguageAppScreen.AddWord.name) {
                AddWordScreen(
                    modifier = Modifier.fillMaxHeight(),
                    onBackClicked = { navController.navigate(LanguageAppScreen.Dictionary.name) },
                    onSaveClicked = { word, translation -> navController.navigate(LanguageAppScreen.Dictionary.name) },
                    userId = userId,
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    widthDp = 320,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "MyAppPreviewDark",
)
@Preview
@Composable
fun MyAppPreview() {
    AppTheme {
        MyApp(Modifier.fillMaxSize())
    }
}
