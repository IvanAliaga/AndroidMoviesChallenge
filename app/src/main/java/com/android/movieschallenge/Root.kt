package com.android.movieschallenge

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.android.movieschallenge.presentation.LoaderScreen
import com.android.movieschallenge.presentation.login.LoginScreen
import com.android.movieschallenge.presentation.movies.detail.DetailMovieScreen
import com.android.movieschallenge.presentation.movies.movie.MoviesScreenStateFlow

@Composable
fun Root() {
    // Creación de NavController
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screens.LOGIN.name
    ) {
        composable(Screens.LOGIN.name) {
            LoginScreen(
                toMovies = {navController.navigate(Screens.MOVIES_SCREEN_STATE_FLOW.name)}
            )
        }

        composable(Screens.LOADER_SCREEN.name){
            LoaderScreen(toMovies = {navController.navigate(Screens.MOVIES_SCREEN_STATE_FLOW.name)})
        }

        composable(Screens.MOVIES_SCREEN_STATE_FLOW.name) {
            MoviesScreenStateFlow { id ->
                navController.navigate(Screens.MOVIE_DETAIL.name + "/$id")
            }
        }

        composable(Screens.MOVIE_DETAIL.name + "/{id}",
                arguments = listOf(navArgument("id") { type = NavType.IntType })){ it ->
            it.arguments?.getInt("id")?.let {
                DetailMovieScreen(id = it)
            }
        }

    }
}

enum class Screens {
    LOGIN,
    LOADER_SCREEN,
    MOVIES_SCREEN_STATE_FLOW,
    MOVIE_DETAIL
}