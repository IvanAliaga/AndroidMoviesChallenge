package com.android.movieschallenge.presentation

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp

@Composable
fun LoaderFullScreen() {
    val screenWidthDp = LocalConfiguration.current.screenWidthDp
    var expanded by remember { mutableStateOf(false) }
    val width = animateDpAsState(
        targetValue = if (expanded) screenWidthDp.dp else 50.dp,
        // Permite especificar cómo deben interpolarse los valores durante la animación.
        //de 0 a 100 de tipo FastOutSlowInEasing, empieza rápido y termina lento
        animationSpec = tween(durationMillis = 1000), label = "animateDpAsStateWidth"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val color = Color.Black.copy(alpha = 0.5f)
        Box(
            modifier = Modifier
                .size(width.value, width.value)
                .background(color = color,
                    shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
    // Operación asíncrona dentro de un componente Compose
    LaunchedEffect(Unit) {
        expanded = true
    }
}