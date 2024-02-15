package com.android.movieschallenge

import androidx.compose.runtime.Composable
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.android.movieschallenge.presentation.LoaderScreen
import com.android.movieschallenge.presentation.login.LoginScreen
import com.android.movieschallenge.presentation.movies.DetailMovieScreen
import com.android.movieschallenge.presentation.movies.MoviesScreen

@Composable
fun Root(lifeCycleOwner: LifecycleOwner) {
    // Create NavController
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screens.LOGIN.name
    ) {
        composable(Screens.LOGIN.name) {
            LoginScreen(lifeCycleOwner,
                toMovies = {navController.navigate(Screens.MOVIES.name)}
            )
        }

        composable(Screens.LOADER_SCREEN.name){
            LoaderScreen(toMovies = {navController.navigate(Screens.MOVIES.name)})
        }

        composable(Screens.MOVIES.name) {
            MoviesScreen(lifeCycleOwner){id ->
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
    MOVIES,
    MOVIE_DETAIL
}