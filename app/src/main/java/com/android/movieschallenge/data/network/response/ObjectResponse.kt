package com.android.movieschallenge.data.network.response

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.google.gson.annotations.SerializedName
import kotlinx.collections.immutable.ImmutableList


@Stable
@Immutable
data class ObjectResponse (
    @SerializedName("page") val page: Int,
    @SerializedName("results") val results: ImmutableList<MovieResponse>
)