package com.ernest.bible

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ernest.bible.ui.theme.BibleTheme

object Destinations {
    const val INTRO_SCREEN = "intro_screen"
    const val BOOK_LIST_SCREEN = "book_list_screen"
    const val CHAPTER_READER = "chapter_reader/{bookName}/{chapterNumber}"
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BibleTheme {
                val viewModel: BibleViewModel = viewModel(
                    factory = BibleViewModel.Factory(application)
                )
                AppNavigation(viewModel)
            }
        }
    }
}

@Composable
fun AppNavigation(viewModel: BibleViewModel) {
    val navController = rememberNavController()
    val state = viewModel.uiState

    NavHost(
        navController = navController,
        startDestination = Destinations.INTRO_SCREEN
    ) {
        composable(Destinations.INTRO_SCREEN) {
            IntroScreen()

            LaunchedEffect(state.isLoading) {
                if (!state.isLoading) {
                    navController.navigate(Destinations.BOOK_LIST_SCREEN) {
                        popUpTo(Destinations.INTRO_SCREEN) { inclusive = true }
                    }
                }
            }
        }

        composable(Destinations.BOOK_LIST_SCREEN) {
            BookListScreen(
                viewState = state,
                onBookClicked = { book, chapter ->
                    navController.navigate("chapter_reader/$book/$chapter")
                }
            )
        }

        composable(Destinations.CHAPTER_READER) { backStackEntry ->
            val bookName = backStackEntry.arguments?.getString("bookName") ?: "Unknown Book"
            val chapterNumber = backStackEntry.arguments?.getString("chapterNumber")?.toIntOrNull() ?: 1

            ChapterReaderScreen(
                bookName = bookName,
                chapterNumber = chapterNumber,
                dao = viewModel.dao,
                onBackPressed = { navController.popBackStack() }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    BibleTheme {
        IntroScreen()
    }
}