package com.android.movieschallenge.presentation.movies

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun DetailMovieScreen(id: Int) {
    Column {
        Text(text = "id :$id", color = Color.Black)
    }
}