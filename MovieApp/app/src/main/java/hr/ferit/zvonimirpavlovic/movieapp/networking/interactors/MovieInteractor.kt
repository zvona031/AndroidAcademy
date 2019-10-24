package hr.ferit.zvonimirpavlovic.movieapp.networking.interactors

import hr.ferit.zvonimirpavlovic.movieapp.model.Movie
import hr.ferit.zvonimirpavlovic.movieapp.model.response.MoviesResponse
import hr.ferit.zvonimirpavlovic.movieapp.model.response.ReviewsResponse
import retrofit2.Callback

interface MovieInteractor {

    fun getPopularMovies(popularMoviesCallback: Callback<MoviesResponse>)

    fun getTopMovies(topMoviesCallback: Callback<MoviesResponse>)

    fun getMovie(movieId: Int, movieCallback: Callback<Movie>)

    fun getReviewsForMovie(movieId: Int, callback: Callback<ReviewsResponse>)

}