package com.android.movieschallenge.presentation.movies

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.android.movieschallenge.di.Constants
import com.android.movieschallenge.domain.model.Movie
import com.android.movieschallenge.presentation.LoaderFullScreen
import com.android.movieschallenge.presentation.movies.movies.MoviesViewModel

@Composable
fun DetailMovieScreen(id: Int) {
    val lifeCycleOwner = LocalLifecycleOwner.current
    val viewModel: MoviesViewModel = hiltViewModel()
    val movie = remember { mutableStateOf<Movie?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(id) {
        viewModel.getMovie(id)
    }

    LaunchedEffect(movie.value) {
        if (movie.value != null) {
            isLoading = false
        }
    }
    viewModel.movie.observe(lifeCycleOwner) { it ->
        movie.value = it
    }
    Column {
        if (isLoading) {
            LoaderFullScreen()
        } else {
            movie.value?.let { movie ->
                Box() {
                    AsyncImage(
                        model = Constants.BASE_IMAGE_URL + movie.posterPath,
                        contentDescription = movie.title,
                        modifier = Modifier
                            .fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    val colorStops = arrayOf(
                        0.0f to Color.Transparent,
                        0.8f to Color(0x33000000),
                        1f to Color(0x99000000)
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                brush = Brush.verticalGradient(
                                    colorStops = colorStops,
                                )
                            )
                    ) {
                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(40.dp, 0.dp, 40.dp, 80.dp)
                        ) {
                            Column {
                                Text(
                                    text = movie.title,
                                    color = Color.White,
                                    fontSize = 50.sp,
                                    fontFamily = FontFamily.SansSerif,
                                    fontWeight = FontWeight.ExtraBold
                                )
                                Text(
                                    text = "Premiere: " + movie.releaseDate,
                                    color = Color.White,
                                    fontSize = 20.sp,
                                    fontFamily = FontFamily.SansSerif,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    text = "Vote Average: " + movie.voteAverage,
                                    color = Color.White,
                                    fontSize = 20.sp,
                                    fontFamily = FontFamily.SansSerif,
                                    fontWeight = FontWeight.Black
                                )
                                Text(
                                    text = movie.overview,
                                    color = Color.White,
                                    fontSize = 20.sp,
                                    fontFamily = FontFamily.SansSerif,
                                    fontWeight = FontWeight.Light,
                                    textAlign = TextAlign.Justify
                                )
                            }
                        }
                    }

                }
            }
        }
    }
}