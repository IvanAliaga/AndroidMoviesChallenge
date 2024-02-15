package com.android.movieschallenge.domain.usecase

import com.android.movieschallenge.data.database.MovieRepository
import com.android.movieschallenge.data.database.entity.toDatabase
import com.android.movieschallenge.domain.model.Movie
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(private val repository: MovieRepository)  {
    suspend operator fun invoke(page: Int):List<Movie>{
        /*
        * Se prioriza que la data sea del api
        * si no existe un response por parte del api
        * pasa a obtener de la base de datos interna
        * de lo contrario la data obtenida es insertada
        * a la base de datos interna pero antes la bd se limpia
        * */
        val movies = repository.getAllMoviesFromApi(page)
        return if(movies.isEmpty()){
            repository.getAllMoviesFromDatabase()
        }else{
            repository.clearMovies()
            repository.insertMovies(movies.map { it.toDatabase() })
            movies
        }
    }
}
